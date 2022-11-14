package kz.utelkhan.bookexchange.model.userDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@Setter
@Entity(name = "ROLE_")
@Table(name = "ROLE_")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String roleName;

    //Bi-directional
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "role",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<User> users;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="ROLE_PERMISSIONS_",
            joinColumns={@JoinColumn(name="role_id")},
            inverseJoinColumns={@JoinColumn(name="permission_id")})
    private Set<Permission> permissions;


    public Role(String roleName) {
        this.roleName = roleName;
    }

    public void setUsers(List<User> users) {
        if(users!=null){
            users.stream().forEach(user -> user.setRole(this));
        }
        this.users = users;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
    public void registerUser(User user){
        users.add(user);
    }
}