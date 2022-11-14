package kz.utelkhan.bookexchange.model.userDetails;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Entity(name = "PERMISSION_")
@Table(name = "PERMISSION_")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Permission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String permission;


    public Permission(String permission) {
        this.permission = permission;
    }


}
