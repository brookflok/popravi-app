package com.damir.popravi.web.rest;

import com.damir.popravi.domain.GrupacijaPoruka;
import com.damir.popravi.repository.GrupacijaPorukaRepository;
import com.damir.popravi.repository.search.GrupacijaPorukaSearchRepository;
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
 * REST controller for managing {@link com.damir.popravi.domain.GrupacijaPoruka}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GrupacijaPorukaResource {

    private final Logger log = LoggerFactory.getLogger(GrupacijaPorukaResource.class);

    private static final String ENTITY_NAME = "grupacijaPoruka";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupacijaPorukaRepository grupacijaPorukaRepository;

    private final GrupacijaPorukaSearchRepository grupacijaPorukaSearchRepository;

    public GrupacijaPorukaResource(GrupacijaPorukaRepository grupacijaPorukaRepository, GrupacijaPorukaSearchRepository grupacijaPorukaSearchRepository) {
        this.grupacijaPorukaRepository = grupacijaPorukaRepository;
        this.grupacijaPorukaSearchRepository = grupacijaPorukaSearchRepository;
    }

    /**
     * {@code POST  /grupacija-porukas} : Create a new grupacijaPoruka.
     *
     * @param grupacijaPoruka the grupacijaPoruka to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupacijaPoruka, or with status {@code 400 (Bad Request)} if the grupacijaPoruka has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupacija-porukas")
    public ResponseEntity<GrupacijaPoruka> createGrupacijaPoruka(@RequestBody GrupacijaPoruka grupacijaPoruka) throws URISyntaxException {
        log.debug("REST request to save GrupacijaPoruka : {}", grupacijaPoruka);
        if (grupacijaPoruka.getId() != null) {
            throw new BadRequestAlertException("A new grupacijaPoruka cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupacijaPoruka result = grupacijaPorukaRepository.save(grupacijaPoruka);
        grupacijaPorukaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/grupacija-porukas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupacija-porukas} : Updates an existing grupacijaPoruka.
     *
     * @param grupacijaPoruka the grupacijaPoruka to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupacijaPoruka,
     * or with status {@code 400 (Bad Request)} if the grupacijaPoruka is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupacijaPoruka couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupacija-porukas")
    public ResponseEntity<GrupacijaPoruka> updateGrupacijaPoruka(@RequestBody GrupacijaPoruka grupacijaPoruka) throws URISyntaxException {
        log.debug("REST request to update GrupacijaPoruka : {}", grupacijaPoruka);
        if (grupacijaPoruka.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupacijaPoruka result = grupacijaPorukaRepository.save(grupacijaPoruka);
        grupacijaPorukaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupacijaPoruka.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grupacija-porukas} : get all the grupacijaPorukas.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupacijaPorukas in body.
     */
    @GetMapping("/grupacija-porukas")
    public List<GrupacijaPoruka> getAllGrupacijaPorukas(@RequestParam(required = false) String filter) {
        if ("chat-is-null".equals(filter)) {
            log.debug("REST request to get all GrupacijaPorukas where chat is null");
            return StreamSupport
                .stream(grupacijaPorukaRepository.findAll().spliterator(), false)
                .filter(grupacijaPoruka -> grupacijaPoruka.getChat() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all GrupacijaPorukas");
        return grupacijaPorukaRepository.findAll();
    }

    /**
     * {@code GET  /grupacija-porukas/:id} : get the "id" grupacijaPoruka.
     *
     * @param id the id of the grupacijaPoruka to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupacijaPoruka, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupacija-porukas/{id}")
    public ResponseEntity<GrupacijaPoruka> getGrupacijaPoruka(@PathVariable Long id) {
        log.debug("REST request to get GrupacijaPoruka : {}", id);
        Optional<GrupacijaPoruka> grupacijaPoruka = grupacijaPorukaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grupacijaPoruka);
    }

    /**
     * {@code DELETE  /grupacija-porukas/:id} : delete the "id" grupacijaPoruka.
     *
     * @param id the id of the grupacijaPoruka to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupacija-porukas/{id}")
    public ResponseEntity<Void> deleteGrupacijaPoruka(@PathVariable Long id) {
        log.debug("REST request to delete GrupacijaPoruka : {}", id);
        grupacijaPorukaRepository.deleteById(id);
        grupacijaPorukaSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/grupacija-porukas?query=:query} : search for the grupacijaPoruka corresponding
     * to the query.
     *
     * @param query the query of the grupacijaPoruka search.
     * @return the result of the search.
     */
    @GetMapping("/_search/grupacija-porukas")
    public List<GrupacijaPoruka> searchGrupacijaPorukas(@RequestParam String query) {
        log.debug("REST request to search GrupacijaPorukas for query {}", query);
        return StreamSupport
            .stream(grupacijaPorukaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
