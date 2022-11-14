package kz.utelkhan.bookexchange.jpa.bookDetails;

import kz.utelkhan.bookexchange.model.bookDetails.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
