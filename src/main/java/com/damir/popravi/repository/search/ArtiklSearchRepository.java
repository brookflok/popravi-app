package com.damir.popravi.repository.search;

import com.damir.popravi.domain.Artikl;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Artikl} entity.
 */
public interface ArtiklSearchRepository extends ElasticsearchRepository<Artikl, Long> {
}
