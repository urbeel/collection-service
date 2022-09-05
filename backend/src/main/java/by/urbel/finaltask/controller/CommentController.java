package by.urbel.finaltask.controller;

import by.urbel.finaltask.dto.requests.CommentRequest;
import by.urbel.finaltask.dto.response.CommentResponse;
import by.urbel.finaltask.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody CommentRequest commentRequest, Authentication authentication) {
        CommentResponse commentResponse = commentService.save(commentRequest, authentication.getName());
        messagingTemplate.convertAndSend("/topic/comment", commentResponse);
    }
}
