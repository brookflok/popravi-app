package com.damir.popravi.repository;

import com.damir.popravi.domain.Lokacija;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Lokacija entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LokacijaRepository extends JpaRepository<Lokacija, Long> {
}
