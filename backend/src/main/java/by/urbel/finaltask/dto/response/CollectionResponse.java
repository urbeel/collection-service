package by.urbel.finaltask.dto.response;

import by.urbel.finaltask.domain.FieldType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CollectionResponse {
    private Long id;
    private String name;
    private String description;
    private String topic;
    private String imageUrl;
    private String author;
    private List<ItemCardResponse> items;
    private List<FieldType> defaultFieldTypes;
}
