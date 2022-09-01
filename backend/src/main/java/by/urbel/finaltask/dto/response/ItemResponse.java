package by.urbel.finaltask.dto.response;

import by.urbel.finaltask.domain.item.ItemField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ItemResponse {
    private Long id;
    private String name;
    private List<String> tags;
    private Date createdDate;
    private List<ItemField> fields;
    private List<CommentResponse> comments;
}
