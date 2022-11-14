package kz.utelkhan.bookexchange.model.userDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import kz.utelkhan.bookexchange.model.bookDetails.Book;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@Entity(name = "USER_")
@Table(name = "USER_")

@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String email;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "roleId")
    @JsonBackReference
    private Role role;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "statusId")
    @JsonBackReference
    private Status status;
    //Bi-directional
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "owner",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Book> ownCollectionOfBooks= new ArrayList<>();

    public User(String firstName, String lastName, String userName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }


    public void setOwnCollectionOfBooks(List<Book> ownCollectionOfBooks) {
        if(ownCollectionOfBooks!=null){
            ownCollectionOfBooks.stream().forEach(book -> book.setOwner(this));
        }
        this.ownCollectionOfBooks = ownCollectionOfBooks;
    }
}
