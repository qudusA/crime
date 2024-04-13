package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.dto.CrimeTypeCountDTO;
import org.fexisaf.crimerecordmanagementsystem.entity.ComplainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<ComplainEntity, Long> {


    @Query("""
        SELECT NEW org.fexisaf.crimerecordmanagementsystem.dto.CrimeTypeCountDTO(c.crimeType, COUNT(c))
        FROM ComplainEntity c
        GROUP BY c.crimeType
        HAVING COUNT(c) = (
            SELECT MAX(subQuery.count)
            FROM (
                SELECT COUNT(innerC) AS count
                FROM ComplainEntity innerC
                GROUP BY innerC.crimeType
            ) AS subQuery
        )
    """)
    List<CrimeTypeCountDTO> findHighestComplaint();


    @Query(value = """
        SELECT *
        FROM complain_entity
        WHERE crime_type = :filterValue
            OR location = :filterValue
            OR "created-date" = :timestampValue
    """, nativeQuery = true)
    List<ComplainEntity> findData(@Param("filterValue") String filterValue, @Param("timestampValue") Timestamp timestampValue);


    @Query(
            value = """
        SELECT * FROM complain_entity
        WHERE is_addressed = ?1 AND "created-date" > ?2
        """,
            nativeQuery = true
    )
    List<ComplainEntity> findAllByIsAddressedAndCreatedDate(boolean b,
                                                            LocalDateTime minus);
}



