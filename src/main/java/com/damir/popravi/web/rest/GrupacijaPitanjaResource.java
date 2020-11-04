package com.damir.popravi.web.rest;

import com.damir.popravi.domain.GrupacijaPitanja;
import com.damir.popravi.repository.GrupacijaPitanjaRepository;
import com.damir.popravi.repository.search.GrupacijaPitanjaSearchRepository;
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
 * REST controller for managing {@link com.damir.popravi.domain.GrupacijaPitanja}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GrupacijaPitanjaResource {

    private final Logger log = LoggerFactory.getLogger(GrupacijaPitanjaResource.class);

    private static final String ENTITY_NAME = "grupacijaPitanja";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupacijaPitanjaRepository grupacijaPitanjaRepository;

    private final GrupacijaPitanjaSearchRepository grupacijaPitanjaSearchRepository;

    public GrupacijaPitanjaResource(GrupacijaPitanjaRepository grupacijaPitanjaRepository, GrupacijaPitanjaSearchRepository grupacijaPitanjaSearchRepository) {
        this.grupacijaPitanjaRepository = grupacijaPitanjaRepository;
        this.grupacijaPitanjaSearchRepository = grupacijaPitanjaSearchRepository;
    }

    /**
     * {@code POST  /grupacija-pitanjas} : Create a new grupacijaPitanja.
     *
     * @param grupacijaPitanja the grupacijaPitanja to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupacijaPitanja, or with status {@code 400 (Bad Request)} if the grupacijaPitanja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupacija-pitanjas")
    public ResponseEntity<GrupacijaPitanja> createGrupacijaPitanja(@RequestBody GrupacijaPitanja grupacijaPitanja) throws URISyntaxException {
        log.debug("REST request to save GrupacijaPitanja : {}", grupacijaPitanja);
        if (grupacijaPitanja.getId() != null) {
            throw new BadRequestAlertException("A new grupacijaPitanja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupacijaPitanja result = grupacijaPitanjaRepository.save(grupacijaPitanja);
        grupacijaPitanjaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/grupacija-pitanjas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupacija-pitanjas} : Updates an existing grupacijaPitanja.
     *
     * @param grupacijaPitanja the grupacijaPitanja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupacijaPitanja,
     * or with status {@code 400 (Bad Request)} if the grupacijaPitanja is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupacijaPitanja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupacija-pitanjas")
    public ResponseEntity<GrupacijaPitanja> updateGrupacijaPitanja(@RequestBody GrupacijaPitanja grupacijaPitanja) throws URISyntaxException {
        log.debug("REST request to update GrupacijaPitanja : {}", grupacijaPitanja);
        if (grupacijaPitanja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupacijaPitanja result = grupacijaPitanjaRepository.save(grupacijaPitanja);
        grupacijaPitanjaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupacijaPitanja.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grupacija-pitanjas} : get all the grupacijaPitanjas.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupacijaPitanjas in body.
     */
    @GetMapping("/grupacija-pitanjas")
    public List<GrupacijaPitanja> getAllGrupacijaPitanjas(@RequestParam(required = false) String filter) {
        if ("artikl-is-null".equals(filter)) {
            log.debug("REST request to get all GrupacijaPitanjas where artikl is null");
            return StreamSupport
                .stream(grupacijaPitanjaRepository.findAll().spliterator(), false)
                .filter(grupacijaPitanja -> grupacijaPitanja.getArtikl() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all GrupacijaPitanjas");
        return grupacijaPitanjaRepository.findAll();
    }

    /**
     * {@code GET  /grupacija-pitanjas/:id} : get the "id" grupacijaPitanja.
     *
     * @param id the id of the grupacijaPitanja to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupacijaPitanja, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupacija-pitanjas/{id}")
    public ResponseEntity<GrupacijaPitanja> getGrupacijaPitanja(@PathVariable Long id) {
        log.debug("REST request to get GrupacijaPitanja : {}", id);
        Optional<GrupacijaPitanja> grupacijaPitanja = grupacijaPitanjaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grupacijaPitanja);
    }

    /**
     * {@code DELETE  /grupacija-pitanjas/:id} : delete the "id" grupacijaPitanja.
     *
     * @param id the id of the grupacijaPitanja to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupacija-pitanjas/{id}")
    public ResponseEntity<Void> deleteGrupacijaPitanja(@PathVariable Long id) {
        log.debug("REST request to delete GrupacijaPitanja : {}", id);
        grupacijaPitanjaRepository.deleteById(id);
        grupacijaPitanjaSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/grupacija-pitanjas?query=:query} : search for the grupacijaPitanja corresponding
     * to the query.
     *
     * @param query the query of the grupacijaPitanja search.
     * @return the result of the search.
     */
    @GetMapping("/_search/grupacija-pitanjas")
    public List<GrupacijaPitanja> searchGrupacijaPitanjas(@RequestParam String query) {
        log.debug("REST request to search GrupacijaPitanjas for query {}", query);
        return StreamSupport
            .stream(grupacijaPitanjaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
