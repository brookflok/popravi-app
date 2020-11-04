package com.damir.popravi.repository.search;

import com.damir.popravi.domain.Poruka;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Poruka} entity.
 */
public interface PorukaSearchRepository extends ElasticsearchRepository<Poruka, Long> {
}
