package kz.utelkhan.bookexchange.controller.user;

import kz.utelkhan.bookexchange.model.bookDetails.Author;
import kz.utelkhan.bookexchange.model.bookDetails.Book;
import kz.utelkhan.bookexchange.model.bookDetails.Genre;
import kz.utelkhan.bookexchange.model.userDetails.Role;
import kz.utelkhan.bookexchange.model.userDetails.Status;
import kz.utelkhan.bookexchange.model.userDetails.User;
import kz.utelkhan.bookexchange.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/user")
@Slf4j
@PreAuthorize("has")
public class UserController {
    User currentUser;

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
    @PreAuthorize("hasAuthority('userPermission')")
    public String findAllUsers(Model model) {
        currentUser = userService.getCurrentUser();
        List<User> userList = userService.findAllUsers();
        userList = userList.stream().filter(user -> !user.getRole().getRoleName().equals("ADMIN")).collect(Collectors.toList());
        userList.forEach(user -> System.out.println(user.getRole().getRoleName()));
        model.addAttribute("users", userList);
        model.addAttribute("currentUser", currentUser);
        return "user/userDetails/user-list";
    }

    //user update
    @GetMapping("/user-update/{id}")
    @PreAuthorize("hasAuthority('userPermission')")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        Status status = userService.getUserStatus(user.getStatus().getId());
        log.warn(status.getStatusName());
        Role role = userService.getUserRole(user.getRole().getId());
        model.addAttribute("user", user);
        model.addAttribute("status", status);
        model.addAttribute("role", role);
        model.addAttribute("currentUser", currentUser);
        return "user/userDetails/user-update";
    }

    @PostMapping("/user-update")
    @PreAuthorize("hasAuthority('userPermission')")
    public String updateUser(User user) {
        userService.update(user);
        return "redirect:/user/";
    }

    //user-info
    @GetMapping("/user-info/{id}")
    @PreAuthorize("hasAuthority('userPermission')")
    public String findAllUsers(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        List<Book> books = user.getOwnCollectionOfBooks();

        model.addAttribute("user", user);
        model.addAttribute("id", id);
        model.addAttribute("books", books);
        model.addAttribute("currentUser", currentUser);
        return "user/userDetails/user-info";
    }

    //user-exchange
    Long b1id = 0l;

    @GetMapping("user-info/user-book/{bid}")
    @PreAuthorize("hasAuthority('userPermission')")
    public String bookExchange(@PathVariable("bid") Long bid, Model model) {
        User user = userService.findUserByBookId(bid);
        model.addAttribute("user", user);
        b1id = bid;

        currentUser = userService.getCurrentUser();
        List<Book> books = userService.getCurrentUser().getOwnCollectionOfBooks();
        model.addAttribute("books", books);
        model.addAttribute("currentUser", currentUser);
        return "user/userDetails/book-exchange";
    }

    @PostMapping("user-info/user-book/{b2id}")
    @PreAuthorize("hasAuthority('userPermission')")
    public String bookExchangeUpdate(@PathVariable("b2id") Long b2id, Model model) {
        userService.exchangeBook(b1id, b2id);
        List<User> userList = userService.findAll();
        model.addAttribute("users", userList);
        return "redirect:/user/";
    }

    //Adding book
    @GetMapping("/user-addAuthor")
    @PreAuthorize("hasAuthority('userPermission')")
    public String addAuthor(Author author) {
        return "user/userDetails/addAuthor";
    }

    @PostMapping("/user-addAuthor")
    @PreAuthorize("hasAuthority('userPermission')")
    public String createAuthor(Author author) {
        userService.addAuthor(author);
        return "redirect:/user/user-addGenre";
    }

    @GetMapping("/user-addGenre")
    @PreAuthorize("hasAuthority('userPermission')")
    public String addGenre(Genre genre) {
        return "/user/userDetails/addGenre";
    }

    @PostMapping("/user-addGenre")
    @PreAuthorize("hasAuthority('userPermission')")
    public String createGenre(Genre genre) {
        userService.addGenre(genre);
        return "redirect:/user/user-addBook";
    }

    @GetMapping("/user-addBook")
    @PreAuthorize("hasAuthority('userPermission')")
    public String addBook(Book book) {
        return "/user/userDetails/addBook";
    }

    @PostMapping("/user-addBook")
    @PreAuthorize("hasAuthority('userPermission')")
    public String createBook(Book book) {
        userService.addBook(book, currentUser);
        return "redirect:/user/";
    }
}
