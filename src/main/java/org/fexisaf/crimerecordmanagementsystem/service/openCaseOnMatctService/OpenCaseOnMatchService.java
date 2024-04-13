package org.fexisaf.crimerecordmanagementsystem.service.openCaseOnMatctService;

import org.fexisaf.crimerecordmanagementsystem.entity.ComplainEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.OpenCaseOnMatch;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;

public interface OpenCaseOnMatchService {
//    Ok<?> saveData(OpenCaseOnMatch onMatch);

    Ok<?> saveData(OpenCaseOnMatch onMatch);

    boolean findAllService(ComplainEntity complaint);
}
