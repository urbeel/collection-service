package by.urbel.finaltask.repository;

import by.urbel.finaltask.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findAllByItemId(Long itemId);

    void deleteByUserUsernameAndItemId(String username, Long itemId);

    boolean existsByUserUsernameAndItemId(String username, Long itemId);
}
