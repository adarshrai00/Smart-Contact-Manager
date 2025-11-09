package com.scm.controllers;

import com.scm.entity.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.Binding;

@ToString
@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("its home");

        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage() {
        return "services";
    }

    @RequestMapping("/contact")
    public String contactPage() {
        return "contact";
    }


    @RequestMapping("/register")
    public String registerPage(Model model) {
        UserForm userForm = new UserForm();

        model.addAttribute("userForm", userForm);

        return "register";

    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

//    processing form
    @PostMapping("/do-register")
    public String processRegister(@Valid  @ModelAttribute UserForm userForm,BindingResult rBindingResult, HttpSession session  )
    {
  if(rBindingResult.hasErrors())
      return "register";
     User   user=new User();
     user.setName(userForm.getName());
     user.setPassword(userForm.getPassword());
     user.setEmail(userForm.getEmail());
     user.setPhoneNumber(userForm.getPhoneNumber());
     user.setAbout(userForm.getAbout());
     user.setProfilePic("https://media.licdn.com/dms/image/v2/D5603AQG485z6TgfEbw/profile-displayphoto-scale_400_400/B56ZgRl0nbGUAk-/0/1752641785148?e=1762992000&v=beta&t=KuzYwRdtUX7lChQeTTXcc_259l0c7jaxEaS56sS2QpE");

       Message message= Message.builder().content("Registered Successfully").type(MessageType.green).build();
        session.setAttribute("message", message);

     User  saveUser= userService.saveUser(  user);
        System.out.println(saveUser);

        return "redirect:/register";
    }

}