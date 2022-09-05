package by.urbel.finaltask.domain;

import by.urbel.finaltask.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Item> items;

    public Tag(String name) {
        this.name = name;
    }
}
