package by.urbel.finaltask.domain;

import by.urbel.finaltask.domain.item.Item;
import by.urbel.finaltask.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        name = "Likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "item_id"})
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
