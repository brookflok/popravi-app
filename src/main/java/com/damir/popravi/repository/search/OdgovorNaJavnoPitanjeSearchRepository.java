package com.damir.popravi.repository.search;

import com.damir.popravi.domain.OdgovorNaJavnoPitanje;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link OdgovorNaJavnoPitanje} entity.
 */
public interface OdgovorNaJavnoPitanjeSearchRepository extends ElasticsearchRepository<OdgovorNaJavnoPitanje, Long> {
}
