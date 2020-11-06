package com.damir.popravi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link KategorijaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class KategorijaSearchRepositoryMockConfiguration {

    @MockBean
    private KategorijaSearchRepository mockKategorijaSearchRepository;

}
