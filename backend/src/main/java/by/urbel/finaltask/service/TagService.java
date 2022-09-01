package by.urbel.finaltask.service;

import by.urbel.finaltask.domain.Tag;
import by.urbel.finaltask.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<String> findAll() {
        List<Tag> tags = tagRepository.findAll();
        if (tags.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return tags.stream().map(Tag::getName).toList();
        }
    }
}
