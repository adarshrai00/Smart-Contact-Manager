package com.scm.services;


import com.scm.entity.Contact;
import com.scm.entity.User;
import com.scm.repositories.ContactRepo;
import com.scm.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService {
  @Autowired
    private ContactRepo contactRepo;
    @Autowired
    private UserRepo userRepo;


    public Contact SaveContact(Contact contact) {


       String contactId= UUID.randomUUID().toString();
       contact.setId(contactId);
       return contactRepo.save(contact);



    }




      public  void  updateContact (Contact contact)
      {

      }
    public List<Contact> getContacts() {

   return contactRepo.findAll();
    }

    public  void deleteContact(String id)
    {
      contactRepo.deleteById(id);
    }


    List<Contact> searchContact(String name,String email)
    {
        return null;

      }

    public Contact getById(String id)
    {
        return contactRepo.findById(id).orElseThrow(()-> new RuntimeException("No Contact found with id "+id));

    }
    List<Contact> getByUserId(String userId)
    {
     return contactRepo.findByUserId(userId);
    }

     public List<Contact>getByUser(User user)
    {
        return contactRepo.findByUser(user);

    }
}
