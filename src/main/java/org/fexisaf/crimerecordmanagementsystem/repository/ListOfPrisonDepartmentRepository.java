package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.ListOfPrisonDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListOfPrisonDepartmentRepository extends JpaRepository<ListOfPrisonDepartmentEntity, Long> {
    Optional<ListOfPrisonDepartmentEntity> findByDepartment(String operationsDepartment);
}
