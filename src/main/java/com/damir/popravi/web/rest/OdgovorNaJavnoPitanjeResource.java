package com.damir.popravi.web.rest;

import com.damir.popravi.domain.OdgovorNaJavnoPitanje;
import com.damir.popravi.repository.OdgovorNaJavnoPitanjeRepository;
import com.damir.popravi.repository.search.OdgovorNaJavnoPitanjeSearchRepository;
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
 * REST controller for managing {@link com.damir.popravi.domain.OdgovorNaJavnoPitanje}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OdgovorNaJavnoPitanjeResource {

    private final Logger log = LoggerFactory.getLogger(OdgovorNaJavnoPitanjeResource.class);

    private static final String ENTITY_NAME = "odgovorNaJavnoPitanje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OdgovorNaJavnoPitanjeRepository odgovorNaJavnoPitanjeRepository;

    private final OdgovorNaJavnoPitanjeSearchRepository odgovorNaJavnoPitanjeSearchRepository;

    public OdgovorNaJavnoPitanjeResource(OdgovorNaJavnoPitanjeRepository odgovorNaJavnoPitanjeRepository, OdgovorNaJavnoPitanjeSearchRepository odgovorNaJavnoPitanjeSearchRepository) {
        this.odgovorNaJavnoPitanjeRepository = odgovorNaJavnoPitanjeRepository;
        this.odgovorNaJavnoPitanjeSearchRepository = odgovorNaJavnoPitanjeSearchRepository;
    }

    /**
     * {@code POST  /odgovor-na-javno-pitanjes} : Create a new odgovorNaJavnoPitanje.
     *
     * @param odgovorNaJavnoPitanje the odgovorNaJavnoPitanje to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new odgovorNaJavnoPitanje, or with status {@code 400 (Bad Request)} if the odgovorNaJavnoPitanje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/odgovor-na-javno-pitanjes")
    public ResponseEntity<OdgovorNaJavnoPitanje> createOdgovorNaJavnoPitanje(@RequestBody OdgovorNaJavnoPitanje odgovorNaJavnoPitanje) throws URISyntaxException {
        log.debug("REST request to save OdgovorNaJavnoPitanje : {}", odgovorNaJavnoPitanje);
        if (odgovorNaJavnoPitanje.getId() != null) {
            throw new BadRequestAlertException("A new odgovorNaJavnoPitanje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OdgovorNaJavnoPitanje result = odgovorNaJavnoPitanjeRepository.save(odgovorNaJavnoPitanje);
        odgovorNaJavnoPitanjeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/odgovor-na-javno-pitanjes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /odgovor-na-javno-pitanjes} : Updates an existing odgovorNaJavnoPitanje.
     *
     * @param odgovorNaJavnoPitanje the odgovorNaJavnoPitanje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated odgovorNaJavnoPitanje,
     * or with status {@code 400 (Bad Request)} if the odgovorNaJavnoPitanje is not valid,
     * or with status {@code 500 (Internal Server Error)} if the odgovorNaJavnoPitanje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/odgovor-na-javno-pitanjes")
    public ResponseEntity<OdgovorNaJavnoPitanje> updateOdgovorNaJavnoPitanje(@RequestBody OdgovorNaJavnoPitanje odgovorNaJavnoPitanje) throws URISyntaxException {
        log.debug("REST request to update OdgovorNaJavnoPitanje : {}", odgovorNaJavnoPitanje);
        if (odgovorNaJavnoPitanje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OdgovorNaJavnoPitanje result = odgovorNaJavnoPitanjeRepository.save(odgovorNaJavnoPitanje);
        odgovorNaJavnoPitanjeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, odgovorNaJavnoPitanje.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /odgovor-na-javno-pitanjes} : get all the odgovorNaJavnoPitanjes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of odgovorNaJavnoPitanjes in body.
     */
    @GetMapping("/odgovor-na-javno-pitanjes")
    public List<OdgovorNaJavnoPitanje> getAllOdgovorNaJavnoPitanjes(@RequestParam(required = false) String filter) {
        if ("javnopitanje-is-null".equals(filter)) {
            log.debug("REST request to get all OdgovorNaJavnoPitanjes where javnoPitanje is null");
            return StreamSupport
                .stream(odgovorNaJavnoPitanjeRepository.findAll().spliterator(), false)
                .filter(odgovorNaJavnoPitanje -> odgovorNaJavnoPitanje.getJavnoPitanje() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all OdgovorNaJavnoPitanjes");
        return odgovorNaJavnoPitanjeRepository.findAll();
    }

    /**
     * {@code GET  /odgovor-na-javno-pitanjes/:id} : get the "id" odgovorNaJavnoPitanje.
     *
     * @param id the id of the odgovorNaJavnoPitanje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the odgovorNaJavnoPitanje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/odgovor-na-javno-pitanjes/{id}")
    public ResponseEntity<OdgovorNaJavnoPitanje> getOdgovorNaJavnoPitanje(@PathVariable Long id) {
        log.debug("REST request to get OdgovorNaJavnoPitanje : {}", id);
        Optional<OdgovorNaJavnoPitanje> odgovorNaJavnoPitanje = odgovorNaJavnoPitanjeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(odgovorNaJavnoPitanje);
    }

    /**
     * {@code DELETE  /odgovor-na-javno-pitanjes/:id} : delete the "id" odgovorNaJavnoPitanje.
     *
     * @param id the id of the odgovorNaJavnoPitanje to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/odgovor-na-javno-pitanjes/{id}")
    public ResponseEntity<Void> deleteOdgovorNaJavnoPitanje(@PathVariable Long id) {
        log.debug("REST request to delete OdgovorNaJavnoPitanje : {}", id);
        odgovorNaJavnoPitanjeRepository.deleteById(id);
        odgovorNaJavnoPitanjeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/odgovor-na-javno-pitanjes?query=:query} : search for the odgovorNaJavnoPitanje corresponding
     * to the query.
     *
     * @param query the query of the odgovorNaJavnoPitanje search.
     * @return the result of the search.
     */
    @GetMapping("/_search/odgovor-na-javno-pitanjes")
    public List<OdgovorNaJavnoPitanje> searchOdgovorNaJavnoPitanjes(@RequestParam String query) {
        log.debug("REST request to search OdgovorNaJavnoPitanjes for query {}", query);
        return StreamSupport
            .stream(odgovorNaJavnoPitanjeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
