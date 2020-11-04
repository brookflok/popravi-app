package com.damir.popravi.repository.search;

import com.damir.popravi.domain.Kanton;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Kanton} entity.
 */
public interface KantonSearchRepository extends ElasticsearchRepository<Kanton, Long> {
}
