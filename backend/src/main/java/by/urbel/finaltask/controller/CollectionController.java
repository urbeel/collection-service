package by.urbel.finaltask.controller;

import by.urbel.finaltask.dto.requests.CollectionRequest;
import by.urbel.finaltask.dto.response.CollectionResponse;
import by.urbel.finaltask.search.SearchService;
import by.urbel.finaltask.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;
    private final SearchService searchService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> create(@Valid @RequestBody CollectionRequest collectionRequest,
                                            Authentication authentication) {
        CollectionResponse collection = collectionService.create(collectionRequest, authentication.getName());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(collectionService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<CollectionResponse>> findAllByUsername(@RequestParam String username) {
        return ResponseEntity.ok(collectionService.findAllByUsername(username));
    }

    @GetMapping("/top5")
    public ResponseEntity<List<CollectionResponse>> findTop5ByItemsNumber() {
        return ResponseEntity.ok(collectionService.findTopNCollectionsByItemsNumber(5));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        collectionService.delete(id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> delete(@RequestParam String word) {
       return ResponseEntity.ok(searchService.search(word));
    }


}
