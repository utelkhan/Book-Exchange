package kz.utelkhan.bookexchange.model.bookDetails;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "GENRE_")
@Table(name = "GENRE_")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Genre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }
}
