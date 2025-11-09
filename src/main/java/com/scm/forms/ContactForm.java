package com.scm.forms;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {
    private String name, phone, email, address,password,description;
    private boolean favorite;
    private String  linkedinLink,facebookLink;


    private MultipartFile image;

}
