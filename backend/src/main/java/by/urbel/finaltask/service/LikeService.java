package by.urbel.finaltask.service;

import by.urbel.finaltask.domain.item.Item;
import by.urbel.finaltask.domain.Like;
import by.urbel.finaltask.domain.user.User;
import by.urbel.finaltask.repository.ItemRepository;
import by.urbel.finaltask.repository.LikeRepository;
import by.urbel.finaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public void create(Long itemId, String username) {
        if (!likeRepository.existsByUserUsernameAndItemId(username, itemId)) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found."));
            Like like = Like.builder().item(item).user(user).build();
            likeRepository.save(like);
        }
    }

    public List<String> readAllByItemId(Long itemId) {
        return likeRepository.findAllByItemId(itemId)
                .stream().map(like -> like.getUser().getUsername()).toList();
    }

    @Transactional
    public void delete(Long itemId, String username) {
        likeRepository.deleteByUserUsernameAndItemId(username, itemId);
    }
}
