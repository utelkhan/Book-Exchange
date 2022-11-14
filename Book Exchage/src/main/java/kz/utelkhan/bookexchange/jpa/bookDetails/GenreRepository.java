package kz.utelkhan.bookexchange.jpa.bookDetails;

import kz.utelkhan.bookexchange.model.bookDetails.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,Long> {
}
