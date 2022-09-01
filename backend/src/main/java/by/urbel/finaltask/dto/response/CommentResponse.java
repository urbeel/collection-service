package by.urbel.finaltask.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentResponse {
    private Long id;
    private Date createdDate;
    private String author;
    private String content;
}
