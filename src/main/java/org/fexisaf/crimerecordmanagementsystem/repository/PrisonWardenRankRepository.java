package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.WardenRanksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrisonWardenRankRepository extends JpaRepository<WardenRanksEntity,Long> {
    Optional<WardenRanksEntity> findByRank(String rank);
}
