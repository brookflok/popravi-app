package com.damir.popravi.repository.search;

import com.damir.popravi.domain.JavnoPitanje;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link JavnoPitanje} entity.
 */
public interface JavnoPitanjeSearchRepository extends ElasticsearchRepository<JavnoPitanje, Long> {
}
