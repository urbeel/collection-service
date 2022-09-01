package by.urbel.finaltask.dto.requests;

import by.urbel.finaltask.domain.FieldType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ItemRequest {
    private String name;
    private List<String> tags;
    private Long collectionId;
    private List<ItemField> fields;

    @Getter
    @Setter
    public static class ItemField {
        private FieldType type;
        private String stringValue;
        private Long numberValue;
        private Instant dateValue;
        private Boolean booleanValue;
    }
}
