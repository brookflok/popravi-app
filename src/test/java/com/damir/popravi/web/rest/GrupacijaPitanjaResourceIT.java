package com.damir.popravi.web.rest;

import com.damir.popravi.PopraviApp;
import com.damir.popravi.domain.GrupacijaPitanja;
import com.damir.popravi.repository.GrupacijaPitanjaRepository;
import com.damir.popravi.repository.search.GrupacijaPitanjaSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GrupacijaPitanjaResource} REST controller.
 */
@SpringBootTest(classes = PopraviApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GrupacijaPitanjaResourceIT {

    private static final Instant DEFAULT_DATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GrupacijaPitanjaRepository grupacijaPitanjaRepository;

    /**
     * This repository is mocked in the com.damir.popravi.repository.search test package.
     *
     * @see com.damir.popravi.repository.search.GrupacijaPitanjaSearchRepositoryMockConfiguration
     */
    @Autowired
    private GrupacijaPitanjaSearchRepository mockGrupacijaPitanjaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupacijaPitanjaMockMvc;

    private GrupacijaPitanja grupacijaPitanja;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupacijaPitanja createEntity(EntityManager em) {
        GrupacijaPitanja grupacijaPitanja = new GrupacijaPitanja()
            .datum(DEFAULT_DATUM);
        return grupacijaPitanja;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupacijaPitanja createUpdatedEntity(EntityManager em) {
        GrupacijaPitanja grupacijaPitanja = new GrupacijaPitanja()
            .datum(UPDATED_DATUM);
        return grupacijaPitanja;
    }

    @BeforeEach
    public void initTest() {
        grupacijaPitanja = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupacijaPitanja() throws Exception {
        int databaseSizeBeforeCreate = grupacijaPitanjaRepository.findAll().size();
        // Create the GrupacijaPitanja
        restGrupacijaPitanjaMockMvc.perform(post("/api/grupacija-pitanjas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupacijaPitanja)))
            .andExpect(status().isCreated());

        // Validate the GrupacijaPitanja in the database
        List<GrupacijaPitanja> grupacijaPitanjaList = grupacijaPitanjaRepository.findAll();
        assertThat(grupacijaPitanjaList).hasSize(databaseSizeBeforeCreate + 1);
        GrupacijaPitanja testGrupacijaPitanja = grupacijaPitanjaList.get(grupacijaPitanjaList.size() - 1);
        assertThat(testGrupacijaPitanja.getDatum()).isEqualTo(DEFAULT_DATUM);

        // Validate the GrupacijaPitanja in Elasticsearch
        verify(mockGrupacijaPitanjaSearchRepository, times(1)).save(testGrupacijaPitanja);
    }

    @Test
    @Transactional
    public void createGrupacijaPitanjaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupacijaPitanjaRepository.findAll().size();

        // Create the GrupacijaPitanja with an existing ID
        grupacijaPitanja.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupacijaPitanjaMockMvc.perform(post("/api/grupacija-pitanjas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupacijaPitanja)))
            .andExpect(status().isBadRequest());

        // Validate the GrupacijaPitanja in the database
        List<GrupacijaPitanja> grupacijaPitanjaList = grupacijaPitanjaRepository.findAll();
        assertThat(grupacijaPitanjaList).hasSize(databaseSizeBeforeCreate);

        // Validate the GrupacijaPitanja in Elasticsearch
        verify(mockGrupacijaPitanjaSearchRepository, times(0)).save(grupacijaPitanja);
    }


    @Test
    @Transactional
    public void getAllGrupacijaPitanjas() throws Exception {
        // Initialize the database
        grupacijaPitanjaRepository.saveAndFlush(grupacijaPitanja);

        // Get all the grupacijaPitanjaList
        restGrupacijaPitanjaMockMvc.perform(get("/api/grupacija-pitanjas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupacijaPitanja.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())));
    }
    
    @Test
    @Transactional
    public void getGrupacijaPitanja() throws Exception {
        // Initialize the database
        grupacijaPitanjaRepository.saveAndFlush(grupacijaPitanja);

        // Get the grupacijaPitanja
        restGrupacijaPitanjaMockMvc.perform(get("/api/grupacija-pitanjas/{id}", grupacijaPitanja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupacijaPitanja.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGrupacijaPitanja() throws Exception {
        // Get the grupacijaPitanja
        restGrupacijaPitanjaMockMvc.perform(get("/api/grupacija-pitanjas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupacijaPitanja() throws Exception {
        // Initialize the database
        grupacijaPitanjaRepository.saveAndFlush(grupacijaPitanja);

        int databaseSizeBeforeUpdate = grupacijaPitanjaRepository.findAll().size();

        // Update the grupacijaPitanja
        GrupacijaPitanja updatedGrupacijaPitanja = grupacijaPitanjaRepository.findById(grupacijaPitanja.getId()).get();
        // Disconnect from session so that the updates on updatedGrupacijaPitanja are not directly saved in db
        em.detach(updatedGrupacijaPitanja);
        updatedGrupacijaPitanja
            .datum(UPDATED_DATUM);

        restGrupacijaPitanjaMockMvc.perform(put("/api/grupacija-pitanjas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrupacijaPitanja)))
            .andExpect(status().isOk());

        // Validate the GrupacijaPitanja in the database
        List<GrupacijaPitanja> grupacijaPitanjaList = grupacijaPitanjaRepository.findAll();
        assertThat(grupacijaPitanjaList).hasSize(databaseSizeBeforeUpdate);
        GrupacijaPitanja testGrupacijaPitanja = grupacijaPitanjaList.get(grupacijaPitanjaList.size() - 1);
        assertThat(testGrupacijaPitanja.getDatum()).isEqualTo(UPDATED_DATUM);

        // Validate the GrupacijaPitanja in Elasticsearch
        verify(mockGrupacijaPitanjaSearchRepository, times(1)).save(testGrupacijaPitanja);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupacijaPitanja() throws Exception {
        int databaseSizeBeforeUpdate = grupacijaPitanjaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupacijaPitanjaMockMvc.perform(put("/api/grupacija-pitanjas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupacijaPitanja)))
            .andExpect(status().isBadRequest());

        // Validate the GrupacijaPitanja in the database
        List<GrupacijaPitanja> grupacijaPitanjaList = grupacijaPitanjaRepository.findAll();
        assertThat(grupacijaPitanjaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GrupacijaPitanja in Elasticsearch
        verify(mockGrupacijaPitanjaSearchRepository, times(0)).save(grupacijaPitanja);
    }

    @Test
    @Transactional
    public void deleteGrupacijaPitanja() throws Exception {
        // Initialize the database
        grupacijaPitanjaRepository.saveAndFlush(grupacijaPitanja);

        int databaseSizeBeforeDelete = grupacijaPitanjaRepository.findAll().size();

        // Delete the grupacijaPitanja
        restGrupacijaPitanjaMockMvc.perform(delete("/api/grupacija-pitanjas/{id}", grupacijaPitanja.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupacijaPitanja> grupacijaPitanjaList = grupacijaPitanjaRepository.findAll();
        assertThat(grupacijaPitanjaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GrupacijaPitanja in Elasticsearch
        verify(mockGrupacijaPitanjaSearchRepository, times(1)).deleteById(grupacijaPitanja.getId());
    }

    @Test
    @Transactional
    public void searchGrupacijaPitanja() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        grupacijaPitanjaRepository.saveAndFlush(grupacijaPitanja);
        when(mockGrupacijaPitanjaSearchRepository.search(queryStringQuery("id:" + grupacijaPitanja.getId())))
            .thenReturn(Collections.singletonList(grupacijaPitanja));

        // Search the grupacijaPitanja
        restGrupacijaPitanjaMockMvc.perform(get("/api/_search/grupacija-pitanjas?query=id:" + grupacijaPitanja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupacijaPitanja.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())));
    }
}
