package com.damir.popravi.repository;

import com.damir.popravi.domain.Kategorija;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Kategorija entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KategorijaRepository extends JpaRepository<Kategorija, Long> {
}
