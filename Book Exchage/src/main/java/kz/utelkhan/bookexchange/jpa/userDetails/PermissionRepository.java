package kz.utelkhan.bookexchange.jpa.userDetails;

import kz.utelkhan.bookexchange.model.userDetails.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
