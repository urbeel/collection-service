package by.urbel.finaltask.search;

import by.urbel.finaltask.domain.item.Item;
import by.urbel.finaltask.dto.response.ItemResponse;
import by.urbel.finaltask.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {
    private final EntityManager entityManager;
    private final ItemMapper itemMapper;

    public List<ItemResponse> search(String text) {
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Item> result = searchSession.search(Item.class)
                .where(factory -> factory.match().fields("name").matching(text)).fetchAll();
        return itemMapper.map(result.hits());
    }
}