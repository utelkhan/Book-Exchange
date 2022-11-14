package kz.utelkhan.bookexchange.jpa.userDetails;

import kz.utelkhan.bookexchange.model.userDetails.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByUserName(String userName);
    public void deleteUserById(Long id);
}
