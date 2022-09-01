package by.urbel.finaltask.mapper;

import by.urbel.finaltask.domain.Comment;
import by.urbel.finaltask.dto.requests.CommentRequest;
import by.urbel.finaltask.dto.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "content", target = "content")
    Comment map(CommentRequest commentRequest);

    @Mapping(source = "content", target = "content")
    @Mapping(source = "user.username", target = "author")
    CommentResponse map(Comment comment);
}
