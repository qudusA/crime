package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.ListOfPrisonFacility;
import org.fexisaf.crimerecordmanagementsystem.entity.PoliceWardenJudgeEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.WardenRanksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrisonRepository extends JpaRepository<PoliceWardenJudgeEntity, Long> {


    List<PoliceWardenJudgeEntity> findAllByWardenRanks(WardenRanksEntity id);

    List<PoliceWardenJudgeEntity> findAllByListOFPrisonFacility(ListOfPrisonFacility id);


    @Query(
            value = """
SELECT pwje.* FROM police_warden_judge_entity pwje
JOIN public.user_entity ue on pwje.user_id = ue.id
WHERE ue.id = ?1
""",nativeQuery = true
    )
    Optional<PoliceWardenJudgeEntity> findByUserEntity(Long id);


}
