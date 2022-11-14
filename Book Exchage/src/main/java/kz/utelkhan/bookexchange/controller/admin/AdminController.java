package kz.utelkhan.bookexchange.controller.admin;

import kz.utelkhan.bookexchange.model.bookDetails.Book;
import kz.utelkhan.bookexchange.model.userDetails.Role;
import kz.utelkhan.bookexchange.model.userDetails.Status;
import kz.utelkhan.bookexchange.model.userDetails.User;
import kz.utelkhan.bookexchange.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequestMapping("/admin")
@Controller
@PreAuthorize("has")
public class AdminController {
    User currentAdmin;

    //User------------------------------------------------------
    UserServiceImpl userService;
    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }
    //-



    //User------------------------------------------------------
        //user-list
        @GetMapping("/")
        @PreAuthorize("hasAuthority('adminPermission')")
        public String findAllUsers(Model model){
            currentAdmin=userService.getCurrentUser();
            List<User> users=userService.findAllUsers().stream().sorted(Comparator.comparing(user -> user.getRole().getRoleName())).collect(Collectors.toList());
            model.addAttribute("users",users);
            model.addAttribute("admin",currentAdmin);
            return "admin/userDetails/user-list";
        }

    //user update
    @GetMapping("/user-update/{id}")
    @PreAuthorize("hasAuthority('adminPermission')")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        log.info("1.INFO and id = "+ userService.getUserStatus(userService.findToUpdate(id).getStatus().getId()));
        User user = userService.findToUpdate(id);
        log.info("2.INFO and id = "+ userService.getUserStatus(user.getStatus().getId()));

        Status status = userService.getUserStatus(user.getStatus().getId());
        log.info("3.INFO and id = "+ userService.getUserStatus(user.getStatus().getId()));

        Role role = userService.getUserRole(user.getRole().getId());
        log.info("4.INFO and id = "+ userService.getUserStatus(user.getStatus().getId()));


        model.addAttribute("user", user);
        log.info("5.INFO and id = "+ userService.getUserStatus(user.getStatus().getId()));

        model.addAttribute("status", status);
        log.info("6.INFO and id = "+ userService.getUserStatus(user.getStatus().getId()));

        model.addAttribute("role", role);
        log.info("7.INFO and id = "+ userService.getUserStatus(user.getStatus().getId()));

        model.addAttribute("admin",currentAdmin);
        log.info("8.INFO and id = "+ userService.getUserStatus(user.getStatus().getId()).getId());

        return "admin/userDetails/user-update";
    }

    @PostMapping("/user-update")
    @PreAuthorize("hasAuthority('adminPermission')")
    public String updateUser(User user){
        userService.update(user);
        return "redirect:/admin/";
    }

        //block
        @PostMapping("/user-block/{id}")
        @PreAuthorize("hasAuthority('adminPermission')")
        public String blockById(@PathVariable Long id,Model model){
            userService.blockUserById(id);
            model.addAttribute("users",userService.findAll());

            return "redirect:/admin/";
        }
        //activate
        @PostMapping("user-activate/{id}")
        @PreAuthorize("hasAuthority('adminPermission')")
        public String activateById(@PathVariable Long id,Model model){
            userService.activateUserById(id);
            model.addAttribute("users",userService.findAll());
            return "redirect:/admin/";
        }

        //user-info
        @GetMapping("/user-info/{id}")
        @PreAuthorize("hasAuthority('adminPermission')")
        public String findAllUsers(@PathVariable Long id, Model model){
            User user = userService.findById(id);
            Status status = userService.getUserStatus(user.getStatus().getId());
            Role role = userService.getUserRole(user.getRole().getId());
            List<Book> books=user.getOwnCollectionOfBooks();

            model.addAttribute("user", user);
            model.addAttribute("status", status);
            model.addAttribute("role", role);
            model.addAttribute("books", books);
            model.addAttribute("admin",currentAdmin);
            return "admin/userDetails/user-info";
        }
        @GetMapping("/user-delete/{id}")
        @PreAuthorize("hasAuthority('adminPermission')")
        public String deleteUser(@PathVariable("id") Long id){
            log.info("DELETE.INFO and id = "+ userService.getUserStatus(userService.findById(2l).getStatus().getId()).getId());

            userService.deleteById(id);

            return "redirect:/admin/";
        }
}
