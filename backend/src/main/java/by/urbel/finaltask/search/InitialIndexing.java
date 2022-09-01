package by.urbel.finaltask.search;

import by.urbel.finaltask.domain.Collection;
import by.urbel.finaltask.domain.item.Item;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InitialIndexing implements ApplicationListener<ApplicationReadyEvent> {
    private final EntityManager entityManager;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        log.info("Initiating indexing...");
        SearchSession searchSession = Search.session( entityManager );
        MassIndexer indexer = searchSession.massIndexer( Item.class );
        try {
            indexer.startAndWait();
            log.info("All entities indexed");
        } catch (InterruptedException e) {
            log.error("Indexing is fail");
            throw new RuntimeException(e);
        }
    }
}
