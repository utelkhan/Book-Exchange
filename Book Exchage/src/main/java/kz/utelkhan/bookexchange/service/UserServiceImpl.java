package kz.utelkhan.bookexchange.service;

//import kz.utelkhan.bookexchange.jms.OrderTransaction;
import kz.utelkhan.bookexchange.jpa.bookDetails.AuthorRepository;
import kz.utelkhan.bookexchange.jpa.bookDetails.BookRepository;
import kz.utelkhan.bookexchange.jpa.bookDetails.GenreRepository;
import kz.utelkhan.bookexchange.jpa.userDetails.RoleRepository;
import kz.utelkhan.bookexchange.jpa.userDetails.StatusRepository;
import kz.utelkhan.bookexchange.jpa.userDetails.UserRepository;
import kz.utelkhan.bookexchange.model.bookDetails.Author;
import kz.utelkhan.bookexchange.model.bookDetails.Book;
import kz.utelkhan.bookexchange.model.bookDetails.Genre;
import kz.utelkhan.bookexchange.model.userDetails.Role;
import kz.utelkhan.bookexchange.model.userDetails.Status;
import kz.utelkhan.bookexchange.model.userDetails.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl {
    UserRepository userRepository;
    StatusRepository statusRepository;
    RoleRepository roleRepository;
    JmsTemplate jmsTemplate;
    PasswordEncoder passwordEncoder;
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    GenreRepository genreRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, StatusRepository statusRepository, RoleRepository roleRepository, JmsTemplate jmsTemplate, PasswordEncoder passwordEncoder, BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.roleRepository = roleRepository;
        this.jmsTemplate = jmsTemplate;
        this.passwordEncoder = passwordEncoder;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    @Transactional
    public User getCurrentUser(){
        return userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    }
    @Transactional
    public User findById(Long id){
        log.info(id.toString());

        return userRepository.findById(id).get();
    }
    @Transactional
    public Status getUserStatus(Long id){
        return findById(id).getStatus();
    }
    @Transactional
    public Role getUserRole(Long id){
        return findById(id).getRole();
    }
    @Transactional
    public void blockUserById(Long id){
        User user=userRepository.getById(id);
        if(!user.getRole().getRoleName().equals("ADMIN"))
        user.setStatus(statusRepository.getById(2l));
        userRepository.save(user);
    }
    @Transactional
    public void activateUserById(Long id){
        User user=userRepository.getById(id);
        user.setStatus(statusRepository.getById(1l));
        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
    Status status;
    Role role;
    @Transactional
    public User findToUpdate(Long id) {

        status=getUserStatus(id);
        role=getUserRole(id);
        return userRepository.findById(id).get();
    }
    @Transactional
    public void update(User user){
        try {
            user.setStatus(status);
            user.setRole(role);
            userRepository.save(user);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        role=null;
        status=null;
    }

    @Transactional
    public boolean createUser(User user){
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUserName();
        String password = user.getPassword();
        String email = user.getEmail();
        user.setStatus(statusRepository.getById(1l));
        user.setRole(roleRepository.getById(1l));
        if (firstName != null && firstName.length() > 0 &&
                lastName != null && lastName.length() > 0 &&
                userName != null && userName.length() > 0 &&
                password != null && password.length() > 0 &&
                email != null && email.length() > 0) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    Book book1;
    Book book2;
    @Transactional
    public void exchangeBook(Long b1id,Long b2id){
        if(!b1id.equals(b2id)){
            book2=bookRepository.getById(b2id);
            book1=bookRepository.getById(b1id);
            User user1=findUserByBookId(book1.getId());
            User user2=findUserByBookId(book2.getId());
            book1.setThisBookAvailableForExchange(false);
            book2.setThisBookAvailableForExchange(false);
            book1.setOwner(user2);
            book2.setOwner(user1);
        }

    }
    @Transactional
    public User findUserByBookId(Long id){
        Book book = bookRepository.findById(id).get();
        return book.getOwner();
    }

    //adding book
    Author bookAuthor;
    Genre bookGenre;
    @Transactional
    public void addAuthor(Author author){
        bookAuthor=author;
    }
    @Transactional
    public void addGenre(Genre genre) {
        bookGenre=genre;
    }
    @Transactional
    public void addBook(Book book,User user) {
        Set<Genre> genresSet=new HashSet<>();
        genresSet.add(bookGenre);
        book.setGenres(genresSet);

        Set<Author> authorsSet=new HashSet<>();
        authorsSet.add(bookAuthor);
        book.setAuthors(authorsSet);

        book.setThisBookAvailableForExchange(true);
        book.setOwner(user);
        bookRepository.save(book);
    }
    @Transactional
    public void deleteById(Long id){
        if(userRepository.findById(id).get().getRole().getRoleName().equals("USER")) {
            userRepository.deleteById(id);
        }
    }
}
