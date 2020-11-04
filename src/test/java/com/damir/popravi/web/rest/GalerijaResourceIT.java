package com.damir.popravi.web.rest;

import com.damir.popravi.PopraviApp;
import com.damir.popravi.domain.Galerija;
import com.damir.popravi.repository.GalerijaRepository;
import com.damir.popravi.repository.search.GalerijaSearchRepository;

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
 * Integration tests for the {@link GalerijaResource} REST controller.
 */
@SpringBootTest(classes = PopraviApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GalerijaResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GalerijaRepository galerijaRepository;

    /**
     * This repository is mocked in the com.damir.popravi.repository.search test package.
     *
     * @see com.damir.popravi.repository.search.GalerijaSearchRepositoryMockConfiguration
     */
    @Autowired
    private GalerijaSearchRepository mockGalerijaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGalerijaMockMvc;

    private Galerija galerija;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Galerija createEntity(EntityManager em) {
        Galerija galerija = new Galerija()
            .title(DEFAULT_TITLE)
            .created(DEFAULT_CREATED);
        return galerija;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Galerija createUpdatedEntity(EntityManager em) {
        Galerija galerija = new Galerija()
            .title(UPDATED_TITLE)
            .created(UPDATED_CREATED);
        return galerija;
    }

    @BeforeEach
    public void initTest() {
        galerija = createEntity(em);
    }

    @Test
    @Transactional
    public void createGalerija() throws Exception {
        int databaseSizeBeforeCreate = galerijaRepository.findAll().size();
        // Create the Galerija
        restGalerijaMockMvc.perform(post("/api/galerijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galerija)))
            .andExpect(status().isCreated());

        // Validate the Galerija in the database
        List<Galerija> galerijaList = galerijaRepository.findAll();
        assertThat(galerijaList).hasSize(databaseSizeBeforeCreate + 1);
        Galerija testGalerija = galerijaList.get(galerijaList.size() - 1);
        assertThat(testGalerija.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGalerija.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the Galerija in Elasticsearch
        verify(mockGalerijaSearchRepository, times(1)).save(testGalerija);
    }

    @Test
    @Transactional
    public void createGalerijaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = galerijaRepository.findAll().size();

        // Create the Galerija with an existing ID
        galerija.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGalerijaMockMvc.perform(post("/api/galerijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galerija)))
            .andExpect(status().isBadRequest());

        // Validate the Galerija in the database
        List<Galerija> galerijaList = galerijaRepository.findAll();
        assertThat(galerijaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Galerija in Elasticsearch
        verify(mockGalerijaSearchRepository, times(0)).save(galerija);
    }


    @Test
    @Transactional
    public void getAllGalerijas() throws Exception {
        // Initialize the database
        galerijaRepository.saveAndFlush(galerija);

        // Get all the galerijaList
        restGalerijaMockMvc.perform(get("/api/galerijas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galerija.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getGalerija() throws Exception {
        // Initialize the database
        galerijaRepository.saveAndFlush(galerija);

        // Get the galerija
        restGalerijaMockMvc.perform(get("/api/galerijas/{id}", galerija.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(galerija.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGalerija() throws Exception {
        // Get the galerija
        restGalerijaMockMvc.perform(get("/api/galerijas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGalerija() throws Exception {
        // Initialize the database
        galerijaRepository.saveAndFlush(galerija);

        int databaseSizeBeforeUpdate = galerijaRepository.findAll().size();

        // Update the galerija
        Galerija updatedGalerija = galerijaRepository.findById(galerija.getId()).get();
        // Disconnect from session so that the updates on updatedGalerija are not directly saved in db
        em.detach(updatedGalerija);
        updatedGalerija
            .title(UPDATED_TITLE)
            .created(UPDATED_CREATED);

        restGalerijaMockMvc.perform(put("/api/galerijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGalerija)))
            .andExpect(status().isOk());

        // Validate the Galerija in the database
        List<Galerija> galerijaList = galerijaRepository.findAll();
        assertThat(galerijaList).hasSize(databaseSizeBeforeUpdate);
        Galerija testGalerija = galerijaList.get(galerijaList.size() - 1);
        assertThat(testGalerija.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGalerija.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the Galerija in Elasticsearch
        verify(mockGalerijaSearchRepository, times(1)).save(testGalerija);
    }

    @Test
    @Transactional
    public void updateNonExistingGalerija() throws Exception {
        int databaseSizeBeforeUpdate = galerijaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGalerijaMockMvc.perform(put("/api/galerijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galerija)))
            .andExpect(status().isBadRequest());

        // Validate the Galerija in the database
        List<Galerija> galerijaList = galerijaRepository.findAll();
        assertThat(galerijaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Galerija in Elasticsearch
        verify(mockGalerijaSearchRepository, times(0)).save(galerija);
    }

    @Test
    @Transactional
    public void deleteGalerija() throws Exception {
        // Initialize the database
        galerijaRepository.saveAndFlush(galerija);

        int databaseSizeBeforeDelete = galerijaRepository.findAll().size();

        // Delete the galerija
        restGalerijaMockMvc.perform(delete("/api/galerijas/{id}", galerija.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Galerija> galerijaList = galerijaRepository.findAll();
        assertThat(galerijaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Galerija in Elasticsearch
        verify(mockGalerijaSearchRepository, times(1)).deleteById(galerija.getId());
    }

    @Test
    @Transactional
    public void searchGalerija() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        galerijaRepository.saveAndFlush(galerija);
        when(mockGalerijaSearchRepository.search(queryStringQuery("id:" + galerija.getId())))
            .thenReturn(Collections.singletonList(galerija));

        // Search the galerija
        restGalerijaMockMvc.perform(get("/api/_search/galerijas?query=id:" + galerija.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galerija.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));
    }
}
