package org.fexisaf.crimerecordmanagementsystem.service.crimeService;

import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.fexisaf.crimerecordmanagementsystem.model.CrimeModel;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CrimeService {


    Ok<?> reportCrime(CrimeModel crimeModel,
                      MultipartFile imgFile,
                      MultipartFile videoFile) throws IOException;


    Ok<?> getCrimeById(Long crimeId) throws IOException;

    Ok<?> getAllByCrimeDate(String crimeDate);

    Ok<?> getAllCrimeByCrimeLocation(String crimeLocation);

    Ok<?> getAllCrimeByCrimeType(String crimeType);

    Ok<?> getAllCrimeByInvestigator(UserEntity investigator);

//Todo find all

    Ok<?> getAllCrimeReport();

    Ok<?> deleteById(Long crimeId);

    Ok<?> updateById(CrimeModel crimeModel, Long crimeId,
                     MultipartFile imgFile,MultipartFile videoFile) throws IOException;

//    Ok<?> getAllNonAnonymousCrimeReport();
//



//
//    Ok<?> getAllAnonymousCrimeReport();
//
//    List<CrimeEntity> getAllIsOpenedCase(boolean isCaseOpened);
//
//
//    void setIsOpenAuto(boolean b, CrimeEntity e);

}
