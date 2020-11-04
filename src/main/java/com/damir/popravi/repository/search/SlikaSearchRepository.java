package com.damir.popravi.repository.search;

import com.damir.popravi.domain.Slika;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Slika} entity.
 */
public interface SlikaSearchRepository extends ElasticsearchRepository<Slika, Long> {
}
