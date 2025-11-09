package com.scm.services;


import com.scm.Exceptions.ResourceNotFoundException;
import com.scm.entity.User;
import com.scm.forms.UserForm;
import com.scm.helper.AppConstants;
import com.scm.repositories.UserRepo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class UserService {
    @Autowired
    private  UserRepo userRepo;
   @Autowired
   private PasswordEncoder passwordEncoder;

    public  User saveUser(User user)
    {
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           user.setRole(AppConstants.Role_User);
           user.setEnabled(true);
                return userRepo.save(user);

    }

    Optional<User> getUserById(Long id)
    {
         return userRepo.findById(id);
    }
    Optional<User> updateUser(User user)
    {
      User user1= userRepo.findById(user.getId()).orElseThrow(()->new ResourceNotFoundException("resource not found"));
         user1.setName(user.getName());
         user1.setEmail(user.getEmail());
         user1.setPassword(user.getPassword());
         user1.setRole(user.getRole());
         user1.setAbout(user.getAbout());
         user1.setPhoneNumber(user.getPhoneNumber());
         user1.setProfilePic(user.getProfilePic());
         user1.setEnabled(user.isEnabled());
         user1.setEmailVerified(user.isEmailVerified());
         user1.setProviderUserId(user.getProviderUserId());
         user1.setProvider(user.getProvider());
         user1.setPhoneVerified( user.isPhoneVerified());
         userRepo.save(user1);
         return Optional.of(user1);
    }

    void deleteUser(Long id)
    {

         User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("resource not found"));
         userRepo.delete(user);
    }
    boolean isUserExist(long id)
    {
        User user = userRepo.findById(id).orElse(null);
        return user != null;
    }
     boolean isUserExistByEmail(String email)
     {
         Optional<User> user = userRepo.findByEmail(email);

         return user.isPresent();
     }

     List<User> getAllUser()
     {
         return userRepo.findAll();
     }

  public   User getUserByEmail(String email) {

         return userRepo.findByEmail(email).orElse(null);
            }


}
