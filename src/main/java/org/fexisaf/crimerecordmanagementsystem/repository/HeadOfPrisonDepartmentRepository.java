package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.HeadOfPrisonDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadOfPrisonDepartmentRepository extends JpaRepository<HeadOfPrisonDepartmentEntity, Long> {
}
