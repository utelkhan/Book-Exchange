package kz.utelkhan.bookexchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/welcome")
public class MainController {

    @GetMapping("/")
    public String index(Model model){
        return "welcome-page";
    }
}
