package com.damir.popravi.repository.search;

import com.damir.popravi.domain.Usluga;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Usluga} entity.
 */
public interface UslugaSearchRepository extends ElasticsearchRepository<Usluga, Long> {
}
