package com.damir.popravi.repository.search;

import com.damir.popravi.domain.Kategorija;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Kategorija} entity.
 */
public interface KategorijaSearchRepository extends ElasticsearchRepository<Kategorija, Long> {
}
