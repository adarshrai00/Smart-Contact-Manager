package com.scm.controllers;


import com.scm.entity.Contact;
import com.scm.entity.User;
import com.scm.forms.ContactForm;
import com.scm.helper.Helper;
import com.scm.services.ContactService;
import com.scm.services.ImageServic;
import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageServic imageServic;

//add contact  handler

    @RequestMapping("/add")
    public String addContact(Model model) {
        ContactForm contactForm= new ContactForm();

        model.addAttribute("contactForm",contactForm);


        return "user/addContactview";
    }



    @PostMapping("/add")
    public String saveContact(@ModelAttribute ContactForm contactForm ,Authentication authentication )

    {
        String filename= UUID.randomUUID().toString().toString();
          String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        String fileUrl= imageServic.uploadImage(contactForm.getImage(),filename);
        Contact contact= new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setFavorite(contactForm.isFavorite());
        contact.setPhoneNumber(contactForm.getPhone());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setFacebookLink(contactForm.getFacebookLink());
        contact.setCloudinaryImagePublicId(fileUrl);
        contact.setUser(user);
        contact.setPicture(fileUrl);

        // re uncomment after
        contactService.SaveContact(contact);
        return "redirect:/user/contact/add";
    }



    @GetMapping("/view")
    public String viewContacts(Authentication authentication,Model model)
    {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        List<Contact> byUser = contactService.getByUser(user);
        model.addAttribute("contacts",byUser);
        return "user/Contacts";
    }



}


