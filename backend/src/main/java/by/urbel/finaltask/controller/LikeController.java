package by.urbel.finaltask.controller;

import by.urbel.finaltask.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LikeController {
    private final LikeService likeService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestParam Long itemId, Authentication authentication) {
        likeService.create(itemId, authentication.getName());
        messagingTemplate.convertAndSend("/topic/like", "updated");
    }

    @DeleteMapping()
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Long itemId, Authentication authentication) {
        likeService.delete(itemId, authentication.getName());
        messagingTemplate.convertAndSend("/topic/like", "updated");
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findAllByItemId(@RequestParam Long itemId) {
        return ResponseEntity.ok(likeService.readAllByItemId(itemId));
    }
}
