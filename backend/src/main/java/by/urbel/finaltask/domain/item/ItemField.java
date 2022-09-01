package by.urbel.finaltask.domain.item;

import by.urbel.finaltask.domain.FieldType;
import lombok.Data;

import javax.lang.model.type.PrimitiveType;
import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
public class ItemField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private FieldType type;
    @Column()
    private String stringValue;
    @Column()
    private Long numberValue;
    @Column()
    private Date dateValue;
    @Column()
    private Boolean booleanValue;
}
