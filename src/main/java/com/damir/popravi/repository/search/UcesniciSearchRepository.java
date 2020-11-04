package com.damir.popravi.repository.search;

import com.damir.popravi.domain.Ucesnici;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Ucesnici} entity.
 */
public interface UcesniciSearchRepository extends ElasticsearchRepository<Ucesnici, Long> {
}
