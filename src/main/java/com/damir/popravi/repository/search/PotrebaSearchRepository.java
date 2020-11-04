package com.damir.popravi.repository.search;

import com.damir.popravi.domain.Potreba;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Potreba} entity.
 */
public interface PotrebaSearchRepository extends ElasticsearchRepository<Potreba, Long> {
}
