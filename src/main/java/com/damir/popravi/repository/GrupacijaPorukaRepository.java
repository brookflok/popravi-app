package com.damir.popravi.repository;

import com.damir.popravi.domain.GrupacijaPoruka;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GrupacijaPoruka entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupacijaPorukaRepository extends JpaRepository<GrupacijaPoruka, Long> {
}
