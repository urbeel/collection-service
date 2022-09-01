package by.urbel.finaltask.dto;

import by.urbel.finaltask.domain.ValueType;
import lombok.Data;

@Data
public class FieldDto {
    private ValueType type;
    private String name;
}
