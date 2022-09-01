package by.urbel.finaltask.domain;

import by.urbel.finaltask.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    @Column(nullable = false, unique = true, length = 36)
    private String token;
    @Column
    private Instant expiryDate;
}
