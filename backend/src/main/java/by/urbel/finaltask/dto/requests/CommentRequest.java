package by.urbel.finaltask.dto.requests;

import lombok.Data;

@Data
public class CommentRequest {
    private Long itemId;
    private String content;
}
