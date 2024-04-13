package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.ListOfCourtHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListOfCourtHouseRepository extends JpaRepository<ListOfCourtHouseEntity, Long> {
    Optional<ListOfCourtHouseEntity> findByCourtHouseName(String courtHouse);
}
