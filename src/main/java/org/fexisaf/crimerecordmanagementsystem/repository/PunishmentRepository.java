package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.PunishmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PunishmentRepository extends JpaRepository<PunishmentEntity, Long> {
    Optional<PunishmentEntity> findByPunishmentType(String type);

    List<PunishmentEntity> findAllByPunishmentType(String type);
}
