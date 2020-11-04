package com.damir.popravi.repository;

import com.damir.popravi.domain.Usluga;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Usluga entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UslugaRepository extends JpaRepository<Usluga, Long> {
}
