package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.CaseAssignedToJudge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignCaseToCourtRepository extends JpaRepository<CaseAssignedToJudge, Long> {
}
