package com.wia1002.eGringottsBackEnd.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.wia1002.eGringottsBackEnd.repository.TransactionRepository;

@Service
public class PdfService {
    @Autowired
    private TransactionRepository transactionRepository;

    public ByteArrayInputStream generateTransactionReceipt(String senderAccountNumber, String receiverAccountNumber,
            double transferAmount, double serviceFee, double addedAmount, String reference, String category,
            String senderUsername, String receiverUsername, String datetime, String senderCurrencyType,
            String receiverCurrencyType) throws DocumentException, IOException {

        List<Long> transactionIds = transactionRepository.findTransactionIdByDateOfTransString(datetime);

        if (transactionIds.isEmpty()) {
            throw new NoSuchElementException("No transactions found for the given date and time: " + datetime);
        }

        Long transaction_id = transactionIds.get(0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add top bar with company name and right margin
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, BaseColor.WHITE);

        PdfPTable topBarTable = new PdfPTable(2); // Two columns: one for name, one for margin
        topBarTable.setWidthPercentage(100); // Set to 100% of page width
        float[] columnWidths = { 0.5f, 0.5f }; // Adjust the ratio as needed
        topBarTable.setWidths(columnWidths);

        // Add company name with background color
        PdfPCell companyNameCell = new PdfPCell(new Phrase("E-Gringotts", fontHeader));
        companyNameCell.setBackgroundColor(new BaseColor(44, 62, 80));
        companyNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        companyNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        companyNameCell.setPadding(10);
        companyNameCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        topBarTable.addCell(companyNameCell);

        // Add an empty cell to create the margin on the right
        PdfPCell emptyCell = new PdfPCell();
        emptyCell.setBackgroundColor(new BaseColor(44, 62, 80)); // Same background color for continuity
        emptyCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        topBarTable.addCell(emptyCell);

        document.add(topBarTable);

        // Add logo at the top right of the page using absolute positioning
        String logoPath = "src/main/java/com/wia1002/eGringottsBackEnd/asset/gringotts_logo.jpg";
        Image logo = Image.getInstance(logoPath);
        logo.scaleAbsolute(50, 50); // Adjust the size accordingly
        logo.setAbsolutePosition(document.right() - 60, document.top() - 100); // Position logo at top right
        document.add(logo);
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD);
        Paragraph title = new Paragraph("Magical Transaction", fontTitle);
        title.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(title);

        document.add(Chunk.NEWLINE);
        // document.add(Chunk.NEWLINE); // You can adjust the number of blank lines as
        // needed

        // Add transaction details
        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.setSpacingBefore(10); // Add some space before the table

        Font fontLabel = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
        Font fontDetails = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL);

        addRowToTable(detailsTable, "Transaction ID", "TRX" + transaction_id, fontLabel, fontDetails);
        addRowToTable(detailsTable, "Transfer to", receiverUsername, fontLabel, fontDetails);
        addRowToTable(detailsTable, "Account Number", receiverAccountNumber, fontLabel, fontDetails);
        addRowToTable(detailsTable, "Transfer From", senderUsername, fontLabel, fontDetails);
        addRowToTable(detailsTable, "Account Number", senderAccountNumber, fontLabel, fontDetails);
        addRowToTable(detailsTable, "Amount", transferAmount + " " + senderCurrencyType + "s = " + addedAmount + " "
                + receiverCurrencyType + "s", fontLabel, fontDetails);
        addRowToTable(detailsTable, "Service Fee", serviceFee + " " + senderCurrencyType + "s", fontLabel,
                fontDetails);
        addRowToTable(detailsTable, "Total Amount Paid",
                (transferAmount + serviceFee) + " " + senderCurrencyType + "s",
                fontLabel, fontDetails);
        addRowToTable(detailsTable, "Date", datetime, fontLabel, fontDetails);
        addRowToTable(detailsTable, "Reference", reference, fontLabel, fontDetails);
        addRowToTable(detailsTable, "Category", category, fontLabel, fontDetails);

        document.add(detailsTable);

        // Add footer with notice
        document.add(Chunk.NEWLINE);
        Font fontNotice = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC);
        Paragraph notice = new Paragraph("This is a computer generated receipt, no signature required.", fontNotice);
        notice.setAlignment(Paragraph.ALIGN_CENTER);

        // Add bottom bar with copyright
        Font fontFooter = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.WHITE);
        PdfPCell bottomBarCell = new PdfPCell(new Phrase(
                "Copyright © 2024, E-Gringotts and/or its affiliates. All rights reserved.", fontFooter));
        bottomBarCell.setBackgroundColor(new BaseColor(44, 62, 80));
        bottomBarCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        bottomBarCell.setPadding(10);
        bottomBarCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);

        PdfPTable bottomBarTable = new PdfPTable(1);
        bottomBarTable.setWidthPercentage(100);
        PdfPCell emptyCel = new PdfPCell(notice);
        emptyCel.setHorizontalAlignment(Element.ALIGN_CENTER);

        emptyCel.setMinimumHeight(300);
        emptyCel.setBorder(0);
        bottomBarTable.addCell(emptyCel);
        bottomBarTable.addCell(bottomBarCell);
        document.add(bottomBarTable);
        document.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private void addRowToTable(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        labelCell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align label cell to the right
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        valueCell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align value cell to the left
        table.addCell(valueCell);
    }

}