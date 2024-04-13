package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.CriminalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CriminalRepository extends JpaRepository<CriminalEntity, Long> {

    Optional<CriminalEntity> findByAge(int age);

    Optional<CriminalEntity> findByEmail(String email);

    void deleteByEmail(String email);

    Optional<CriminalEntity> findByFirstNameAndGender(String name, String gender);
}
