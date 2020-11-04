package com.damir.popravi.repository;

import com.damir.popravi.domain.DodatniInfoUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DodatniInfoUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DodatniInfoUserRepository extends JpaRepository<DodatniInfoUser, Long> {
}
