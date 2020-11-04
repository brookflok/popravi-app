package com.damir.popravi.repository;

import com.damir.popravi.domain.MainSlika;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MainSlika entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MainSlikaRepository extends JpaRepository<MainSlika, Long> {
}
