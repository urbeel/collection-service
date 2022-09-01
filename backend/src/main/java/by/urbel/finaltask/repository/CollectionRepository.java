package by.urbel.finaltask.repository;

import by.urbel.finaltask.domain.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findAllByOwnerUsername(String username);

    @Query("select c from Collection c order by c.items.size desc")
    List<Collection> findTopCollectionsByItemsNumber(Pageable limit);

}
