package kz.utelkhan.bookexchange.model.bookDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import kz.utelkhan.bookexchange.model.userDetails.User;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Component
@Entity(name = "BOOK_")
@Table(name = "BOOK_")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private double rating;
    @Column
    private boolean isThisBookAvailableForExchange;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "owner_id")
    @JsonBackReference
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY,  targetEntity = Genre.class,
                    cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "BOOK_GENRES_",
            inverseJoinColumns = @JoinColumn(name = "book_id",
                    nullable = false,
                    updatable = false),
            joinColumns = @JoinColumn(name = "genre_id",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Set<Genre> genres;


    @ManyToMany(fetch = FetchType.LAZY,  targetEntity = Author.class,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "BOOK_AUTHORS_",
            inverseJoinColumns = @JoinColumn(name = "book_id",
                    nullable = false,
                    updatable = false),
            joinColumns = @JoinColumn(name = "author_id",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Set<Author> authors;

    public Book(String title, double rating, boolean isThisBookAvailableForExchange) {
        this.title = title;
        this.rating = rating;
        this.isThisBookAvailableForExchange = isThisBookAvailableForExchange;
    }

}
