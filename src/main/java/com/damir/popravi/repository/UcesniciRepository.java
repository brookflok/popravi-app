package com.damir.popravi.repository;

import com.damir.popravi.domain.Ucesnici;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Ucesnici entity.
 */
@Repository
public interface UcesniciRepository extends JpaRepository<Ucesnici, Long> {

    @Query(value = "select distinct ucesnici from Ucesnici ucesnici left join fetch ucesnici.dodatniInfoUsers",
        countQuery = "select count(distinct ucesnici) from Ucesnici ucesnici")
    Page<Ucesnici> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct ucesnici from Ucesnici ucesnici left join fetch ucesnici.dodatniInfoUsers")
    List<Ucesnici> findAllWithEagerRelationships();

    @Query("select ucesnici from Ucesnici ucesnici left join fetch ucesnici.dodatniInfoUsers where ucesnici.id =:id")
    Optional<Ucesnici> findOneWithEagerRelationships(@Param("id") Long id);
}
