package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.ListOfPoliceStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListOfPoliceStationRepository extends JpaRepository<ListOfPoliceStationEntity,Long> {
    Optional<ListOfPoliceStationEntity> findByPoliceStationName(String policeStationName);
}
