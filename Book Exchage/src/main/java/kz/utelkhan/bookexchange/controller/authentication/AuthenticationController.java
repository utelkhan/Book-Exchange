package kz.utelkhan.bookexchange.controller.authentication;

import kz.utelkhan.bookexchange.model.userDetails.User;
import kz.utelkhan.bookexchange.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Transactional
public class AuthenticationController {
    UserServiceImpl userService;
    @Autowired
    public AuthenticationController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String index(){
        return "authentication/login";
    }
    @GetMapping("/login")
    public String login(){
        return "authentication/login";
    }
    @GetMapping("/welcome-page")
    public String welcome(Model model){
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "welcome-page";
    }

    @GetMapping("/registration")
    public String registration(User user){
        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String createUser(User user){
        if(userService.createUser(user)){
            return "redirect:/login";
        }
        return "authentication/registration";
    }
}
