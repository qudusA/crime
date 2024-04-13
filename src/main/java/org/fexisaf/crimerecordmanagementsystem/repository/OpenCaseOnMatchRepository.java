package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.OpenCaseOnMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpenCaseOnMatchRepository extends JpaRepository<OpenCaseOnMatch, Long> {

    List<OpenCaseOnMatch> findByToMatchContaining(String crimeType);

}
