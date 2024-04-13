package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.PoliceRanksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PoliceRankRepository extends JpaRepository<PoliceRanksEntity, Long> {

    Optional<PoliceRanksEntity> findByRank(String rank);

}
