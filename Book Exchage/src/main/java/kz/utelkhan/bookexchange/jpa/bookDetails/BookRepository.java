package kz.utelkhan.bookexchange.jpa.bookDetails;

import kz.utelkhan.bookexchange.model.bookDetails.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
