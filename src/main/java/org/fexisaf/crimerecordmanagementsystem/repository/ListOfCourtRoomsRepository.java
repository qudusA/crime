package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.ListOfCourtRoomsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListOfCourtRoomsRepository extends JpaRepository<ListOfCourtRoomsEntity, Long> {
    Optional<ListOfCourtRoomsEntity> findByRoomId(Long roomId);
}
