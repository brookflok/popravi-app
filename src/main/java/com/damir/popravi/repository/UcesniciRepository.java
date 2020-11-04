package com.damir.popravi.repository;

import com.damir.popravi.domain.Ucesnici;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Ucesnici entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UcesniciRepository extends JpaRepository<Ucesnici, Long> {
}
