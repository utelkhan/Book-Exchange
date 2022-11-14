package kz.utelkhan.bookexchange.jpa.userDetails;

import kz.utelkhan.bookexchange.model.userDetails.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
