package com.damir.popravi.repository;

import com.damir.popravi.domain.OdgovorNaJavnoPitanje;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OdgovorNaJavnoPitanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OdgovorNaJavnoPitanjeRepository extends JpaRepository<OdgovorNaJavnoPitanje, Long> {
}
