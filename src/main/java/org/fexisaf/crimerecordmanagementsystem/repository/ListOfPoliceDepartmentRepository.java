package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.ListOfPoliceDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListOfPoliceDepartmentRepository extends JpaRepository<ListOfPoliceDepartmentEntity, Long> {
    Optional<ListOfPoliceDepartmentEntity> findByDepartment(String operationsDepartment);
}
