package by.urbel.finaltask.controller;

import by.urbel.finaltask.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<String>> findAll(){
        return ResponseEntity.ok(tagService.findAll());
    }
}
