package com.damir.popravi.repository;

import com.damir.popravi.domain.Artikl;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Artikl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtiklRepository extends JpaRepository<Artikl, Long> {
}
