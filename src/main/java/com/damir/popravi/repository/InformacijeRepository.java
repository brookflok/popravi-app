package com.damir.popravi.repository;

import com.damir.popravi.domain.Informacije;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Informacije entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformacijeRepository extends JpaRepository<Informacije, Long> {
}
