package org.fexisaf.crimerecordmanagementsystem.service.punishmentService;

import org.fexisaf.crimerecordmanagementsystem.model.PunishmentModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;

public interface PunishmentService {

    Ok<?> savePunishment(PunishmentModel punishmentModel, Long chargedCaseId) throws Exception;

    Ok<?> findByPunishmentType(String type) throws NotFoundException;

    Ok<?> deleteById(Long punishmentId) throws Exception;

    Ok<?> updateById(PunishmentModel punishmentModel, Long punishmentId) throws Exception;
}
