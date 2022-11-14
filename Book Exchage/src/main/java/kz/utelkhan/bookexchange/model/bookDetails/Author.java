package kz.utelkhan.bookexchange.model.bookDetails;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "AUTHOR_")
@Table(name = "AUTHOR_")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
