package com.damir.popravi.repository;

import com.damir.popravi.domain.Slika;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Slika entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SlikaRepository extends JpaRepository<Slika, Long> {
}
