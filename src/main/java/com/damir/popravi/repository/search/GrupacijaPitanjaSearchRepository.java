package com.damir.popravi.repository.search;

import com.damir.popravi.domain.GrupacijaPitanja;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link GrupacijaPitanja} entity.
 */
public interface GrupacijaPitanjaSearchRepository extends ElasticsearchRepository<GrupacijaPitanja, Long> {
}
