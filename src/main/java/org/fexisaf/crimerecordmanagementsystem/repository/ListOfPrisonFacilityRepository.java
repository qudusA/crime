package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.ListOfPrisonFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListOfPrisonFacilityRepository extends JpaRepository<ListOfPrisonFacility,Long> {
    Optional<ListOfPrisonFacility> findByPrisonFacilityName(String station);
}
