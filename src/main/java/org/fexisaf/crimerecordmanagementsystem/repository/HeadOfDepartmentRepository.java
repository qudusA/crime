package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.HeadOfDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadOfDepartmentRepository extends JpaRepository<HeadOfDepartmentEntity, Long> {
}
