package org.fexisaf.crimerecordmanagementsystem.service.criminalService;

import org.fexisaf.crimerecordmanagementsystem.model.CriminalModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CriminalService {
    Ok<?> saveCriminal(CriminalModel criminalModel, Long crimeId, MultipartFile imgFile) throws IOException;

//    Ok<?> saveCriminal(CriminalModel criminalModel, Long crimeId,String imgFile) throws IOException;

    Ok<?> findCriminalById(Long id);

    Ok<?> findCriminalByAge(int age) throws IOException;

    Ok<?> findCriminalByEmail(String email) throws IOException;

    Ok<?> findCriminalByNameAndGender(String name, String gender) throws IOException;

    Ok<?> deleteById(Long criminalId) throws NotFoundException;

    Ok<?> deleteByCriminalName(String email);

    Ok<?> updateById(CriminalModel criminalModel, Long criminalId, MultipartFile imgFile) throws IOException;
}
