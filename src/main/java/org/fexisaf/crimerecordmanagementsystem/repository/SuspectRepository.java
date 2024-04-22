package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.SuspectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface SuspectRepository extends JpaRepository<SuspectEntity, Long> {
        Optional<SuspectEntity>  findSuspectEntitiesByDateOfBirthAndFirstNameAndLastName(String dateOfBirth, String firstName, String lastName);



}
