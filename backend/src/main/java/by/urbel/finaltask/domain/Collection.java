package by.urbel.finaltask.domain;

import by.urbel.finaltask.domain.item.Item;
import by.urbel.finaltask.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 60)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String topic;
    @Column()
    private String imageUrl;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;
    @OneToMany(targetEntity = Item.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_id")
    private List<Item> items;
    @OneToMany(targetEntity = FieldType.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_id")
    private List<FieldType> defaultFieldTypes;
}
