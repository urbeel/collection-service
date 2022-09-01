package by.urbel.finaltask.controller;

import by.urbel.finaltask.domain.item.Item;
import by.urbel.finaltask.dto.requests.ItemRequest;
import by.urbel.finaltask.dto.response.ItemResponse;
import by.urbel.finaltask.search.SearchService;
import by.urbel.finaltask.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ItemController {
    private final ItemService itemService;
    private final SearchService searchService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody ItemRequest itemRequest) {
        itemService.create(itemRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> findAllFromCollection(@RequestParam Long collectionId) {
        return ResponseEntity.ok(itemService.getAll(collectionId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        itemService.delete(id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String query){
        List<ItemResponse> items = searchService.search(query);
        return ResponseEntity.ok(items);
    }
}
