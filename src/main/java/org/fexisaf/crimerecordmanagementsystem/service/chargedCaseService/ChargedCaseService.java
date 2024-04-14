package org.fexisaf.crimerecordmanagementsystem.service.chargedCaseService;

import org.fexisaf.crimerecordmanagementsystem.entity.ChargedCaseEntity;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;

public interface ChargedCaseService {

//    void assignJudgeToCase(ChargedCaseEntity event);

    Ok<?> createChargedCase(Long caseId, String CourtHouse) throws NotFoundException;

    Ok<?> findAllJudge();

//    Ok<?> giveVerdict(Long chargedCaseId, VerdictModel verdictModel) throws NotFoundException;

//    Ok<?> updateVerdict(Long chargedCaseId, PunishmentModel punishmentModel) throws NotFoundException;
}
