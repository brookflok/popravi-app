package com.damir.popravi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link GrupacijaPitanjaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GrupacijaPitanjaSearchRepositoryMockConfiguration {

    @MockBean
    private GrupacijaPitanjaSearchRepository mockGrupacijaPitanjaSearchRepository;

}
