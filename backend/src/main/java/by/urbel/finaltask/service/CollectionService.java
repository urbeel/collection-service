package by.urbel.finaltask.service;

import by.urbel.finaltask.domain.Collection;
import by.urbel.finaltask.domain.user.User;
import by.urbel.finaltask.dto.requests.CollectionRequest;
import by.urbel.finaltask.dto.response.CollectionResponse;
import by.urbel.finaltask.mapper.CollectionMapper;
import by.urbel.finaltask.repository.CollectionRepository;
import by.urbel.finaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final CollectionMapper collectionMapper;

    private static final SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");

    public CollectionResponse create(CollectionRequest collectionRequest, String username) {
        Collection collection = collectionMapper.map(collectionRequest);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
        collection.setOwner(user);
        return collectionMapper.map(collectionRepository.save(collection));
    }

    public CollectionResponse findById(Long id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return collectionMapper.map(collection);
    }

    public List<CollectionResponse> findAllByUsername(String username) {
        List<Collection> collections = collectionRepository.findAllByOwnerUsername(username);
        if (collections.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return collectionMapper.map(collections);
    }

    public List<CollectionResponse> findTopNCollectionsByItemsNumber(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Collection> collections = collectionRepository.findTopCollectionsByItemsNumber(pageable);
        if (collections.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return collectionMapper.map(collections);
    }

    public void delete(Long id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals(collection.getOwner().getUsername())
                || authentication.getAuthorities().contains(adminAuthority)) {
            collectionRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
