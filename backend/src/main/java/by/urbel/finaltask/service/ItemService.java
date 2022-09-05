package by.urbel.finaltask.service;

import by.urbel.finaltask.domain.Comment;
import by.urbel.finaltask.domain.item.Item;
import by.urbel.finaltask.dto.requests.ItemRequest;
import by.urbel.finaltask.dto.response.ItemResponse;
import by.urbel.finaltask.mapper.ItemMapper;
import by.urbel.finaltask.repository.CollectionRepository;
import by.urbel.finaltask.repository.FieldTypeRepository;
import by.urbel.finaltask.repository.ItemRepository;
import by.urbel.finaltask.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CollectionRepository collectionRepository;
    private final TagService tagService;
    private final TagRepository tagRepository;
    private final FieldTypeRepository fieldTypeRepository;
    private final ItemMapper itemMapper;

    private static final SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");

    public Item create(ItemRequest itemRequest) {
        Item item = itemMapper.map(itemRequest);
        item.setCollection(collectionRepository.findById(itemRequest.getCollectionId()).orElseThrow());
        item.setTags(tagService.saveTags(itemRequest.getTags()));
        if (item.getFields() != null) {
            item.getFields().forEach(field -> {
                field.setType(fieldTypeRepository.findById(field.getType().getId()).orElseThrow());
            });
        }
        return itemRepository.save(item);
    }

    public ItemResponse findById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        item.getComments().sort(Comparator.comparing(Comment::getCreatedDate).reversed());
        return itemMapper.map(item);
    }

    public List<ItemResponse> findAll(Long collectionId) {
        List<Item> items = itemRepository.findAllByCollectionId(collectionId);
        if (items.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return items.stream().map(itemMapper::map).toList();
    }

    public List<ItemResponse> findLastAdded() {
        List<Item> items = itemRepository.findAllByOrderByCreatedDateDesc();
        if (items.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return items.stream().map(itemMapper::map).toList();
    }

    public List<ItemResponse> findAllByTag(String tag) {
        List<Item> items = tagRepository.findByName(tag)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getItems();
        if (items.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return items.stream().map(itemMapper::map).toList();
    }

    public void delete(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (item.getCollection().getName().equals(authentication.getName())
                || authentication.getAuthorities().contains(adminAuthority)) {
            itemRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
