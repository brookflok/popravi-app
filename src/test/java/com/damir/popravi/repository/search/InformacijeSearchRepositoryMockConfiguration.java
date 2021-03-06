package com.damir.popravi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link InformacijeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InformacijeSearchRepositoryMockConfiguration {

    @MockBean
    private InformacijeSearchRepository mockInformacijeSearchRepository;

}
