package by.urbel.finaltask.dto.requests;

import by.urbel.finaltask.domain.item.Item;
import by.urbel.finaltask.dto.FieldDto;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CollectionRequest {
    private String name;
    private String description;
    private String topic;
    private String imageUrl;
    private Set<Item> items;
    private List<FieldDto> defaultFieldTypes;
}
