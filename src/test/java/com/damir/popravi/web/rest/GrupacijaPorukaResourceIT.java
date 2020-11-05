package com.damir.popravi.web.rest;

import com.damir.popravi.PopraviApp;
import com.damir.popravi.domain.GrupacijaPoruka;
import com.damir.popravi.repository.GrupacijaPorukaRepository;
import com.damir.popravi.repository.search.GrupacijaPorukaSearchRepository;

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
 * Integration tests for the {@link GrupacijaPorukaResource} REST controller.
 */
@SpringBootTest(classes = PopraviApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GrupacijaPorukaResourceIT {

    private static final Instant DEFAULT_DATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GrupacijaPorukaRepository grupacijaPorukaRepository;

    /**
     * This repository is mocked in the com.damir.popravi.repository.search test package.
     *
     * @see com.damir.popravi.repository.search.GrupacijaPorukaSearchRepositoryMockConfiguration
     */
    @Autowired
    private GrupacijaPorukaSearchRepository mockGrupacijaPorukaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupacijaPorukaMockMvc;

    private GrupacijaPoruka grupacijaPoruka;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupacijaPoruka createEntity(EntityManager em) {
        GrupacijaPoruka grupacijaPoruka = new GrupacijaPoruka()
            .datum(DEFAULT_DATUM);
        return grupacijaPoruka;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupacijaPoruka createUpdatedEntity(EntityManager em) {
        GrupacijaPoruka grupacijaPoruka = new GrupacijaPoruka()
            .datum(UPDATED_DATUM);
        return grupacijaPoruka;
    }

    @BeforeEach
    public void initTest() {
        grupacijaPoruka = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupacijaPoruka() throws Exception {
        int databaseSizeBeforeCreate = grupacijaPorukaRepository.findAll().size();
        // Create the GrupacijaPoruka
        restGrupacijaPorukaMockMvc.perform(post("/api/grupacija-porukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupacijaPoruka)))
            .andExpect(status().isCreated());

        // Validate the GrupacijaPoruka in the database
        List<GrupacijaPoruka> grupacijaPorukaList = grupacijaPorukaRepository.findAll();
        assertThat(grupacijaPorukaList).hasSize(databaseSizeBeforeCreate + 1);
        GrupacijaPoruka testGrupacijaPoruka = grupacijaPorukaList.get(grupacijaPorukaList.size() - 1);
        assertThat(testGrupacijaPoruka.getDatum()).isEqualTo(DEFAULT_DATUM);

        // Validate the GrupacijaPoruka in Elasticsearch
        verify(mockGrupacijaPorukaSearchRepository, times(1)).save(testGrupacijaPoruka);
    }

    @Test
    @Transactional
    public void createGrupacijaPorukaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupacijaPorukaRepository.findAll().size();

        // Create the GrupacijaPoruka with an existing ID
        grupacijaPoruka.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupacijaPorukaMockMvc.perform(post("/api/grupacija-porukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupacijaPoruka)))
            .andExpect(status().isBadRequest());

        // Validate the GrupacijaPoruka in the database
        List<GrupacijaPoruka> grupacijaPorukaList = grupacijaPorukaRepository.findAll();
        assertThat(grupacijaPorukaList).hasSize(databaseSizeBeforeCreate);

        // Validate the GrupacijaPoruka in Elasticsearch
        verify(mockGrupacijaPorukaSearchRepository, times(0)).save(grupacijaPoruka);
    }


    @Test
    @Transactional
    public void getAllGrupacijaPorukas() throws Exception {
        // Initialize the database
        grupacijaPorukaRepository.saveAndFlush(grupacijaPoruka);

        // Get all the grupacijaPorukaList
        restGrupacijaPorukaMockMvc.perform(get("/api/grupacija-porukas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupacijaPoruka.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())));
    }
    
    @Test
    @Transactional
    public void getGrupacijaPoruka() throws Exception {
        // Initialize the database
        grupacijaPorukaRepository.saveAndFlush(grupacijaPoruka);

        // Get the grupacijaPoruka
        restGrupacijaPorukaMockMvc.perform(get("/api/grupacija-porukas/{id}", grupacijaPoruka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupacijaPoruka.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGrupacijaPoruka() throws Exception {
        // Get the grupacijaPoruka
        restGrupacijaPorukaMockMvc.perform(get("/api/grupacija-porukas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupacijaPoruka() throws Exception {
        // Initialize the database
        grupacijaPorukaRepository.saveAndFlush(grupacijaPoruka);

        int databaseSizeBeforeUpdate = grupacijaPorukaRepository.findAll().size();

        // Update the grupacijaPoruka
        GrupacijaPoruka updatedGrupacijaPoruka = grupacijaPorukaRepository.findById(grupacijaPoruka.getId()).get();
        // Disconnect from session so that the updates on updatedGrupacijaPoruka are not directly saved in db
        em.detach(updatedGrupacijaPoruka);
        updatedGrupacijaPoruka
            .datum(UPDATED_DATUM);

        restGrupacijaPorukaMockMvc.perform(put("/api/grupacija-porukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrupacijaPoruka)))
            .andExpect(status().isOk());

        // Validate the GrupacijaPoruka in the database
        List<GrupacijaPoruka> grupacijaPorukaList = grupacijaPorukaRepository.findAll();
        assertThat(grupacijaPorukaList).hasSize(databaseSizeBeforeUpdate);
        GrupacijaPoruka testGrupacijaPoruka = grupacijaPorukaList.get(grupacijaPorukaList.size() - 1);
        assertThat(testGrupacijaPoruka.getDatum()).isEqualTo(UPDATED_DATUM);

        // Validate the GrupacijaPoruka in Elasticsearch
        verify(mockGrupacijaPorukaSearchRepository, times(1)).save(testGrupacijaPoruka);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupacijaPoruka() throws Exception {
        int databaseSizeBeforeUpdate = grupacijaPorukaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupacijaPorukaMockMvc.perform(put("/api/grupacija-porukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupacijaPoruka)))
            .andExpect(status().isBadRequest());

        // Validate the GrupacijaPoruka in the database
        List<GrupacijaPoruka> grupacijaPorukaList = grupacijaPorukaRepository.findAll();
        assertThat(grupacijaPorukaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GrupacijaPoruka in Elasticsearch
        verify(mockGrupacijaPorukaSearchRepository, times(0)).save(grupacijaPoruka);
    }

    @Test
    @Transactional
    public void deleteGrupacijaPoruka() throws Exception {
        // Initialize the database
        grupacijaPorukaRepository.saveAndFlush(grupacijaPoruka);

        int databaseSizeBeforeDelete = grupacijaPorukaRepository.findAll().size();

        // Delete the grupacijaPoruka
        restGrupacijaPorukaMockMvc.perform(delete("/api/grupacija-porukas/{id}", grupacijaPoruka.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupacijaPoruka> grupacijaPorukaList = grupacijaPorukaRepository.findAll();
        assertThat(grupacijaPorukaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GrupacijaPoruka in Elasticsearch
        verify(mockGrupacijaPorukaSearchRepository, times(1)).deleteById(grupacijaPoruka.getId());
    }

    @Test
    @Transactional
    public void searchGrupacijaPoruka() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        grupacijaPorukaRepository.saveAndFlush(grupacijaPoruka);
        when(mockGrupacijaPorukaSearchRepository.search(queryStringQuery("id:" + grupacijaPoruka.getId())))
            .thenReturn(Collections.singletonList(grupacijaPoruka));

        // Search the grupacijaPoruka
        restGrupacijaPorukaMockMvc.perform(get("/api/_search/grupacija-porukas?query=id:" + grupacijaPoruka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupacijaPoruka.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())));
    }
}
