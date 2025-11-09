package com.scm.controllers;



import com.scm.entity.User;
import com.scm.helper.Helper;
import org.springframework.ui.Model;
import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;




@Controller
@RequestMapping("/user")
public class UserController {
//    user Dashboard
  @Autowired
  private UserService userService;



    @PostMapping("/dashboard")
    public String  userDashboard(){
        return "user/dashboard";
    }

    @GetMapping("/bokka")
    public String forOauth2Login(){
        return "user/dashboard";
    }
    @GetMapping("/profile")
    public String  userProfile(Model model, Authentication authentication){

//        changes will  do  here
       String username = Helper.getEmailOfLoggedInUser(authentication);
        System.out.println("this is the email if find:"+ username);

         User user=   userService.getUserByEmail(username);
        System.out.println(user.getName()+user.getProvider());
        model.addAttribute("loggedInUser",user);
        return "user/Profile";



    }
    @PostMapping("/pprofile")
    public String pprofile()
    {
        return "user/profile";
    }




//    user add contact page






//    user view contacts






//    user edit contact
}
