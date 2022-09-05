package by.urbel.finaltask.service;

import by.urbel.finaltask.domain.Tag;
import by.urbel.finaltask.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Tag> saveTags(List<String> tagsStr) {
        List<Tag> tags = new ArrayList<>();
        tagsStr.forEach(tagName -> {
            Optional<Tag> tagOptional = tagRepository.findByName(tagName);
            tagOptional.ifPresentOrElse(tags::add, () -> {
                tags.add(tagRepository.save(new Tag(tagName)));
            });
        });
        return tags;
    }
}
