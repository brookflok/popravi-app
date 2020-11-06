package com.damir.popravi.web.rest;

import com.damir.popravi.PopraviApp;
import com.damir.popravi.domain.Kategorija;
import com.damir.popravi.repository.KategorijaRepository;
import com.damir.popravi.repository.search.KategorijaSearchRepository;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link KategorijaResource} REST controller.
 */
@SpringBootTest(classes = PopraviApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class KategorijaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private KategorijaRepository kategorijaRepository;

    /**
     * This repository is mocked in the com.damir.popravi.repository.search test package.
     *
     * @see com.damir.popravi.repository.search.KategorijaSearchRepositoryMockConfiguration
     */
    @Autowired
    private KategorijaSearchRepository mockKategorijaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKategorijaMockMvc;

    private Kategorija kategorija;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kategorija createEntity(EntityManager em) {
        Kategorija kategorija = new Kategorija()
            .name(DEFAULT_NAME);
        return kategorija;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kategorija createUpdatedEntity(EntityManager em) {
        Kategorija kategorija = new Kategorija()
            .name(UPDATED_NAME);
        return kategorija;
    }

    @BeforeEach
    public void initTest() {
        kategorija = createEntity(em);
    }

    @Test
    @Transactional
    public void createKategorija() throws Exception {
        int databaseSizeBeforeCreate = kategorijaRepository.findAll().size();
        // Create the Kategorija
        restKategorijaMockMvc.perform(post("/api/kategorijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kategorija)))
            .andExpect(status().isCreated());

        // Validate the Kategorija in the database
        List<Kategorija> kategorijaList = kategorijaRepository.findAll();
        assertThat(kategorijaList).hasSize(databaseSizeBeforeCreate + 1);
        Kategorija testKategorija = kategorijaList.get(kategorijaList.size() - 1);
        assertThat(testKategorija.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Kategorija in Elasticsearch
        verify(mockKategorijaSearchRepository, times(1)).save(testKategorija);
    }

    @Test
    @Transactional
    public void createKategorijaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kategorijaRepository.findAll().size();

        // Create the Kategorija with an existing ID
        kategorija.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKategorijaMockMvc.perform(post("/api/kategorijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kategorija)))
            .andExpect(status().isBadRequest());

        // Validate the Kategorija in the database
        List<Kategorija> kategorijaList = kategorijaRepository.findAll();
        assertThat(kategorijaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Kategorija in Elasticsearch
        verify(mockKategorijaSearchRepository, times(0)).save(kategorija);
    }


    @Test
    @Transactional
    public void getAllKategorijas() throws Exception {
        // Initialize the database
        kategorijaRepository.saveAndFlush(kategorija);

        // Get all the kategorijaList
        restKategorijaMockMvc.perform(get("/api/kategorijas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kategorija.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getKategorija() throws Exception {
        // Initialize the database
        kategorijaRepository.saveAndFlush(kategorija);

        // Get the kategorija
        restKategorijaMockMvc.perform(get("/api/kategorijas/{id}", kategorija.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kategorija.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingKategorija() throws Exception {
        // Get the kategorija
        restKategorijaMockMvc.perform(get("/api/kategorijas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKategorija() throws Exception {
        // Initialize the database
        kategorijaRepository.saveAndFlush(kategorija);

        int databaseSizeBeforeUpdate = kategorijaRepository.findAll().size();

        // Update the kategorija
        Kategorija updatedKategorija = kategorijaRepository.findById(kategorija.getId()).get();
        // Disconnect from session so that the updates on updatedKategorija are not directly saved in db
        em.detach(updatedKategorija);
        updatedKategorija
            .name(UPDATED_NAME);

        restKategorijaMockMvc.perform(put("/api/kategorijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKategorija)))
            .andExpect(status().isOk());

        // Validate the Kategorija in the database
        List<Kategorija> kategorijaList = kategorijaRepository.findAll();
        assertThat(kategorijaList).hasSize(databaseSizeBeforeUpdate);
        Kategorija testKategorija = kategorijaList.get(kategorijaList.size() - 1);
        assertThat(testKategorija.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Kategorija in Elasticsearch
        verify(mockKategorijaSearchRepository, times(1)).save(testKategorija);
    }

    @Test
    @Transactional
    public void updateNonExistingKategorija() throws Exception {
        int databaseSizeBeforeUpdate = kategorijaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKategorijaMockMvc.perform(put("/api/kategorijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kategorija)))
            .andExpect(status().isBadRequest());

        // Validate the Kategorija in the database
        List<Kategorija> kategorijaList = kategorijaRepository.findAll();
        assertThat(kategorijaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Kategorija in Elasticsearch
        verify(mockKategorijaSearchRepository, times(0)).save(kategorija);
    }

    @Test
    @Transactional
    public void deleteKategorija() throws Exception {
        // Initialize the database
        kategorijaRepository.saveAndFlush(kategorija);

        int databaseSizeBeforeDelete = kategorijaRepository.findAll().size();

        // Delete the kategorija
        restKategorijaMockMvc.perform(delete("/api/kategorijas/{id}", kategorija.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kategorija> kategorijaList = kategorijaRepository.findAll();
        assertThat(kategorijaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Kategorija in Elasticsearch
        verify(mockKategorijaSearchRepository, times(1)).deleteById(kategorija.getId());
    }

    @Test
    @Transactional
    public void searchKategorija() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        kategorijaRepository.saveAndFlush(kategorija);
        when(mockKategorijaSearchRepository.search(queryStringQuery("id:" + kategorija.getId())))
            .thenReturn(Collections.singletonList(kategorija));

        // Search the kategorija
        restKategorijaMockMvc.perform(get("/api/_search/kategorijas?query=id:" + kategorija.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kategorija.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
