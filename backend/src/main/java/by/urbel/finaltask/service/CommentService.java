package by.urbel.finaltask.service;

import by.urbel.finaltask.domain.Comment;
import by.urbel.finaltask.dto.requests.CommentRequest;
import by.urbel.finaltask.dto.response.CommentResponse;
import by.urbel.finaltask.mapper.CommentMapper;
import by.urbel.finaltask.repository.CommentRepository;
import by.urbel.finaltask.repository.ItemRepository;
import by.urbel.finaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CommentMapper commentMapper;

    public CommentResponse save(CommentRequest commentRequest, String username) {
        Comment comment = commentMapper.map(commentRequest);
        comment.setUser(userRepository.findByUsername(username).orElseThrow());
        comment.setItem(itemRepository.findById(commentRequest.getItemId()).orElseThrow());
        return commentMapper.map(commentRepository.save(comment));
    }
}
