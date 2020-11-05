package com.damir.popravi.repository.search;

import com.damir.popravi.domain.GrupacijaPoruka;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link GrupacijaPoruka} entity.
 */
public interface GrupacijaPorukaSearchRepository extends ElasticsearchRepository<GrupacijaPoruka, Long> {
}
