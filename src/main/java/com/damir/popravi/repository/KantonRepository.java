package com.damir.popravi.repository;

import com.damir.popravi.domain.Kanton;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Kanton entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KantonRepository extends JpaRepository<Kanton, Long> {
}
