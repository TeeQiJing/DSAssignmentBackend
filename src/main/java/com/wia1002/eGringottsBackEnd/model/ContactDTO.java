package com.wia1002.eGringottsBackEnd.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ContactDTO {
    private String contact_mobile;
    private String user_created_name;
    private String account_number;
    private String image_path;
    public ContactDTO(String contact_mobile, String user_created_name, String account_number, String image_path) {
        this.contact_mobile = contact_mobile;
        this.user_created_name = user_created_name;
        this.account_number = account_number;
        this.image_path = image_path;
    }
}
