package by.urbel.finaltask.domain.item;

import by.urbel.finaltask.domain.Collection;
import by.urbel.finaltask.domain.Comment;
import by.urbel.finaltask.domain.Like;
import by.urbel.finaltask.domain.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Indexed
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @FullTextField
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "items_tags",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tags;
    @Column
    private Date createdDate;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Collection collection;
    @OneToMany(targetEntity = ItemField.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private List<ItemField> fields;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item", fetch = FetchType.EAGER)
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item",fetch = FetchType.LAZY)
    private List<Like> likes;
    @PrePersist
    private void addCreatedDate() {
        this.createdDate = new Date();
    }
}
