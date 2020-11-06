package com.damir.popravi.web.rest;

import com.damir.popravi.domain.Kategorija;
import com.damir.popravi.repository.KategorijaRepository;
import com.damir.popravi.repository.search.KategorijaSearchRepository;
import com.damir.popravi.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.damir.popravi.domain.Kategorija}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KategorijaResource {

    private final Logger log = LoggerFactory.getLogger(KategorijaResource.class);

    private static final String ENTITY_NAME = "kategorija";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KategorijaRepository kategorijaRepository;

    private final KategorijaSearchRepository kategorijaSearchRepository;

    public KategorijaResource(KategorijaRepository kategorijaRepository, KategorijaSearchRepository kategorijaSearchRepository) {
        this.kategorijaRepository = kategorijaRepository;
        this.kategorijaSearchRepository = kategorijaSearchRepository;
    }

    /**
     * {@code POST  /kategorijas} : Create a new kategorija.
     *
     * @param kategorija the kategorija to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kategorija, or with status {@code 400 (Bad Request)} if the kategorija has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kategorijas")
    public ResponseEntity<Kategorija> createKategorija(@RequestBody Kategorija kategorija) throws URISyntaxException {
        log.debug("REST request to save Kategorija : {}", kategorija);
        if (kategorija.getId() != null) {
            throw new BadRequestAlertException("A new kategorija cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kategorija result = kategorijaRepository.save(kategorija);
        kategorijaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/kategorijas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kategorijas} : Updates an existing kategorija.
     *
     * @param kategorija the kategorija to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kategorija,
     * or with status {@code 400 (Bad Request)} if the kategorija is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kategorija couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kategorijas")
    public ResponseEntity<Kategorija> updateKategorija(@RequestBody Kategorija kategorija) throws URISyntaxException {
        log.debug("REST request to update Kategorija : {}", kategorija);
        if (kategorija.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Kategorija result = kategorijaRepository.save(kategorija);
        kategorijaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kategorija.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kategorijas} : get all the kategorijas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kategorijas in body.
     */
    @GetMapping("/kategorijas")
    public List<Kategorija> getAllKategorijas() {
        log.debug("REST request to get all Kategorijas");
        return kategorijaRepository.findAll();
    }

    /**
     * {@code GET  /kategorijas/:id} : get the "id" kategorija.
     *
     * @param id the id of the kategorija to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kategorija, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kategorijas/{id}")
    public ResponseEntity<Kategorija> getKategorija(@PathVariable Long id) {
        log.debug("REST request to get Kategorija : {}", id);
        Optional<Kategorija> kategorija = kategorijaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kategorija);
    }

    /**
     * {@code DELETE  /kategorijas/:id} : delete the "id" kategorija.
     *
     * @param id the id of the kategorija to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kategorijas/{id}")
    public ResponseEntity<Void> deleteKategorija(@PathVariable Long id) {
        log.debug("REST request to delete Kategorija : {}", id);
        kategorijaRepository.deleteById(id);
        kategorijaSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/kategorijas?query=:query} : search for the kategorija corresponding
     * to the query.
     *
     * @param query the query of the kategorija search.
     * @return the result of the search.
     */
    @GetMapping("/_search/kategorijas")
    public List<Kategorija> searchKategorijas(@RequestParam String query) {
        log.debug("REST request to search Kategorijas for query {}", query);
        return StreamSupport
            .stream(kategorijaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
