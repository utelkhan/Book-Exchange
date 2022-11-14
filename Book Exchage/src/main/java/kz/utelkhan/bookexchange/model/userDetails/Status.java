package kz.utelkhan.bookexchange.model.userDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "STATUS_")
@Table(name = "STATUS_")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status implements Serializable {
    //ACTIVE, BANNED
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String statusName;

    //Bi-directional
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "status",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<User> users;

    public void setUsers(List<User> users) {
        if(users!=null){
            users.stream().forEach(user -> user.setStatus(this));
        }
        this.users = users;
    }

    public Status(String statusName) {
        this.statusName = statusName;
    }
    public void registerUser(User user){
        users.add(user);
    }
}