package com.damir.popravi.repository.search;

import com.damir.popravi.domain.Informacije;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Informacije} entity.
 */
public interface InformacijeSearchRepository extends ElasticsearchRepository<Informacije, Long> {
}
