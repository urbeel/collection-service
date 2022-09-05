package by.urbel.finaltask.domain;

import by.urbel.finaltask.domain.item.Item;
import by.urbel.finaltask.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Date createdDate;
    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.MERGE})
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;
    private String content;

    @PrePersist
    private void addCreatedDate(){
        this.createdDate = new Date();
    }
}
