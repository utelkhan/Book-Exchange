package kz.utelkhan.bookexchange;

import kz.utelkhan.bookexchange.jpa.userDetails.RoleRepository;
import kz.utelkhan.bookexchange.jpa.userDetails.StatusRepository;
import kz.utelkhan.bookexchange.model.bookDetails.Author;
import kz.utelkhan.bookexchange.model.bookDetails.Book;
import kz.utelkhan.bookexchange.model.bookDetails.Genre;
import kz.utelkhan.bookexchange.model.userDetails.Permission;
import kz.utelkhan.bookexchange.model.userDetails.Role;
import kz.utelkhan.bookexchange.model.userDetails.Status;
import kz.utelkhan.bookexchange.model.userDetails.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TransactionRequiredException;
import java.util.*;

@Slf4j
@SpringBootApplication
public class BookExchangeApplication implements CommandLineRunner {
    private Scanner scanner=new Scanner(System.in);
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    StatusRepository statusRepository;
    @Autowired
    public BookExchangeApplication(RoleRepository roleRepository, PasswordEncoder passwordEncoder, StatusRepository statusRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.statusRepository = statusRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookExchangeApplication.class, args);
    }

    @Override
    public void run(String... args) throws TransactionRequiredException {
        System.out.println("\n\n\n\n\n=====================================================================");
        log.info("\n" + new Date().toString() + "\nSpring application was successfully created!!!");
        System.out.println("=====================================================================\n\n\n\n\n");



        //-----------------------Book initializing-------------------------------
        //Genres
        Set<Genre> genresOf1Book=new HashSet<>();
        Genre biography=new Genre("Biography");
        genresOf1Book.add(biography);

        Set<Genre> genresOf2Book=new HashSet<>();
        Genre military=new Genre("Military");
        genresOf2Book.add(military);

        Set<Genre> genresOf3Book=new HashSet<>();
        Genre romance=new Genre("Romance");
        genresOf3Book.add(romance);

        Set<Genre> genresOf4Book=new HashSet<>();
        Genre sinceFiction=new Genre("SinceFiction");
        genresOf4Book.add(sinceFiction);

        //Authors
        Set<Author> authorsOf1Book=new HashSet<>();
        Author author1=new Author("Mukhtar","Auezov");
        authorsOf1Book.add(author1);

        Set<Author> authorsOf2Book=new HashSet<>();
        Author author2=new Author("Bauyrzhan","Momyshuly");
        authorsOf2Book.add(author2);

        Set<Author> authorsOf3Book=new HashSet<>();
        Author author3=new Author("Helen","Hoang");
        authorsOf3Book.add(author3);

        Set<Author> authorsOf4Book=new HashSet<>();
        Author author4=new Author("Jay","Martel");
        authorsOf4Book.add(author4);

        //Books
        Book book1=new Book("Abay zholy", 9.2,true);
        Book book2=new Book("Kanmen zhazylgan kitap", 9.2,true);
        Book book3=new Book("The Kiss Quotient", 9.2,true);
        Book book4=new Book("Channel Blue", 9.2,true);


        book1.setGenres(genresOf1Book);
        book2.setGenres(genresOf2Book);
        book3.setGenres(genresOf3Book);
        book4.setGenres(genresOf4Book);
        book1.setAuthors(authorsOf1Book);
        book2.setAuthors(authorsOf2Book);
        book3.setAuthors(authorsOf3Book);
        book4.setAuthors(authorsOf4Book);


        //-----------------------User initializing-------------------------------
        //Permissions
        Set<Permission> userPermissionsSet=new HashSet<>();
        Permission userPermissions=new Permission("userPermission");
        userPermissionsSet.add(userPermissions);

        Set<Permission> adminPermissionsSet=new HashSet<>();
        Permission adminPermission=new Permission("adminPermission");
        adminPermissionsSet.add(adminPermission);




        //Roles
        Role user=new Role("USER");
        user.setPermissions(userPermissionsSet);

        Role admin=new Role("ADMIN");
        admin.setPermissions(adminPermissionsSet);
        roleRepository.saveAll(Arrays.asList(user,admin));

        //Statuses
        Status active=new Status("ACTIVE");
        Status banned=new Status("BANNED");

        //Users
        User user3=new User("Azamat", "Utelkhan","azamat", passwordEncoder.encode("azamat"),"someEmail@mail.ru");
        User user2=new User("Ernar", "Menglibay","ernar", passwordEncoder.encode("ernar"),"someEmail@mail.ru");
        User user1=new User("Arman", "Sugurali","arman", passwordEncoder.encode("arman"),"someEmail@mail.ru");


        //Cascade Type= All болганы ушын юзерды салса китаптыда сохранить етеди
        user1.setOwnCollectionOfBooks(Arrays.asList(book1,book3));
        user2.setOwnCollectionOfBooks(Arrays.asList(book2,book4));
        user3.setOwnCollectionOfBooks(Arrays.asList());


        active.setUsers(Arrays.asList(user1,user2,user3));//status

        //role
        user.setUsers(Arrays.asList(user1,user2));
        admin.setUsers(Arrays.asList(user3));

        statusRepository.saveAll(Arrays.asList(active,banned));
    }

}
