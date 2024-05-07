package com.wia1002.eGringottsBackEnd.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wia1002.eGringottsBackEnd.exception.ResourceNotFoundException;
import com.wia1002.eGringottsBackEnd.model.UserAvatar;
import com.wia1002.eGringottsBackEnd.repository.UserAvatarRepository;
import com.wia1002.eGringottsBackEnd.service.UserAvatarService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AllArgsConstructor
// @RequestMapping("")
@CrossOrigin("http://localhost:3000/")
public class UserAvatarController {
    @Autowired
    private UserAvatarRepository userAvatarRepository;

    @PostMapping("/uploadimage")
    public ResponseEntity<String> uploadImage(@RequestParam("accountNumber") String accountNumber,
                                              @RequestParam("image_path") MultipartFile image) {
        try {
            UserAvatar userAvatar = new UserAvatar();
            userAvatar.setAccount_number(accountNumber);
            userAvatarRepository.save(userAvatar);

            userAvatar.setImage_path(image.getBytes());
            userAvatarRepository.save(userAvatar);
            return ResponseEntity.ok().body("Image uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping("/{accountNumber}")
    public UserAvatar getImage(@PathVariable String accountNumber) {
        return userAvatarRepository.findById(accountNumber).orElse(null);
    }

}
