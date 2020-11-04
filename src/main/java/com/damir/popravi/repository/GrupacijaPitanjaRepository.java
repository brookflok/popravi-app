package com.damir.popravi.repository;

import com.damir.popravi.domain.GrupacijaPitanja;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GrupacijaPitanja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupacijaPitanjaRepository extends JpaRepository<GrupacijaPitanja, Long> {
}
