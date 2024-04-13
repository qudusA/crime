package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.CrimeEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrimeRepository extends JpaRepository<CrimeEntity, Long> {
    List<CrimeEntity> findAllByCrimeDate(String date);

    List<CrimeEntity> findAllByCrimeLocation(String location);

    List<CrimeEntity> findAllByCrimeType(String crimeType);

    List<CrimeEntity> findAllByInvestigatingOfficer(UserEntity investigator);

}
