package by.urbel.finaltask.domain.user;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private ERole name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}
