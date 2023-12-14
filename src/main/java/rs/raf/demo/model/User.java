package rs.raf.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private boolean can_read_users;

    @Column
    private boolean can_create_users;

    @Column
    private boolean can_update_users;

    @Column
    private boolean can_delete_users;
}
