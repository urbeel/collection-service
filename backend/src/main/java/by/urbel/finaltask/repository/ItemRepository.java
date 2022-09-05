package by.urbel.finaltask.repository;

import by.urbel.finaltask.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCollectionId(Long id);
    List<Item> findAllByOrderByCreatedDateDesc();
}
