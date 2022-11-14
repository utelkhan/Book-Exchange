package kz.utelkhan.bookexchange.jpa.userDetails;

import kz.utelkhan.bookexchange.model.userDetails.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Long> {
}
