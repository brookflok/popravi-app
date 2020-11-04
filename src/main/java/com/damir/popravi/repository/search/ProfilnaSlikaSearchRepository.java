package com.damir.popravi.repository.search;

import com.damir.popravi.domain.ProfilnaSlika;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProfilnaSlika} entity.
 */
public interface ProfilnaSlikaSearchRepository extends ElasticsearchRepository<ProfilnaSlika, Long> {
}
