package org.fexisaf.crimerecordmanagementsystem.service.criminalService;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fexisaf.crimerecordmanagementsystem.entity.CrimeEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.CriminalEntity;
import org.fexisaf.crimerecordmanagementsystem.model.CriminalModel;
import org.fexisaf.crimerecordmanagementsystem.repository.CrimeRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.CriminalRepository;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.util.S3Utility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CriminalServiceImpl implements CriminalService{

    private final CriminalRepository criminalRepository;
    private final CrimeRepository crimeRepository;

    private final AmazonS3 amazonS3;

    @Value("${aws.bucket-name}")
    private String bucketName;

    private final S3Utility s3Utility;

    @Override
    public Ok<?> saveCriminal(CriminalModel criminalModel, Long crimeId, MultipartFile imgFile) throws IOException {
        try {
            if(criminalModel == null) throw new IllegalArgumentException("empty data is not accepted..");

            CrimeEntity crime = crimeRepository.findById(crimeId)
                    .orElseThrow(()-> new NotFoundException("crime not found..."));

            String imageName = null;
            if(imgFile != null) {
                UUID uuid = UUID.randomUUID();
                imageName = "criminal-image/" + criminalModel.getFirstName()
                        + uuid + "-" + imgFile.getOriginalFilename();
                s3Utility.saveToAws(imgFile.getInputStream(), imageName);
            }
            CriminalEntity criminal = CriminalEntity.builder()
                    .age(criminalModel.getAge())
                    .crimeEntities(crime)
                    .address(criminalModel.getAddress())
                    .firstName(criminalModel.getFirstName())
                    .lastName(criminalModel.getLastName())
                    .eyeColor(criminalModel.getEyeColor())
                    .gender(criminalModel.getGender())
                    .contactNumber(criminalModel.getContactNumber())
                    .height(criminalModel.getHeight())
                    .scars(criminalModel.getScars())
                    .dateOfBirth(criminalModel.getDateOfBirth())
                    .tattoos(criminalModel.getTattoos())
                    .photograph(imageName)
                    .weight(criminalModel.getWeight())
                    .nationality(criminalModel.getNationality())
                    .hairColor(criminalModel.getHairColor())
                    .email(criminalModel.getEmail())
                    .build();

           var cre = criminalRepository.save(criminal);
            log.info("from criminal service cre {}", cre);
            return Ok.builder()
                    .date(LocalDateTime.now())
                    .statusCode(HttpStatus.CREATED.value())
                    .statusName(HttpStatus.CREATED.name())
                    .message("Criminal data uploaded successfully.")
                    .build();
        }
        catch (IOException e) {
            throw new IOException("Failed to upload criminal data: " + e.getMessage());
        }
        catch (ExpiredJwtException e){
            System.out.println(e.getMessage());
            throw new JwtException(e.getMessage());
        }
    }


    @Override
    public Ok<?> findCriminalById(Long id) {
       try {
           CriminalEntity obj = criminalRepository.findById(id)
                   .orElseThrow(() -> new NotFoundException("criminal not found..."));

           CriminalEntity criminal = getCriminalEntity(obj);

           return Ok.builder()
                   .statusCode(HttpStatus.OK.value())
                   .statusName(HttpStatus.OK.name())
                   .date(LocalDateTime.now())
                   .message(criminal)
                   .build();
       }catch (NotFoundException e){
           throw new NotFoundException(e.getMessage());
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    private CriminalEntity getCriminalEntity(CriminalEntity obj)
            throws IOException {
        var result = s3Utility.getGetImgAndVidPresignedUrl(obj.getPhotograph(), null);

       return CriminalEntity.builder()
                .email(obj.getEmail())
                .tattoos(obj.getTattoos())
                .scars(obj.getScars())
                .criminalId(obj.getCriminalId())
                .weight(obj.getWeight())
                .gender(obj.getGender())
                .eyeColor(obj.getEyeColor())
                .address(obj.getAddress())
                .photograph(result.imageUrl())
                .contactNumber(obj.getContactNumber())
                .nationality(obj.getNationality())
                .age(obj.getAge())
                .dateOfBirth(obj.getDateOfBirth())
                .crimeEntities(obj.getCrimeEntities())
                  .build();

    }

    @Override
    public Ok<?> findCriminalByAge(int age) throws IOException {
        CriminalEntity obj = criminalRepository.findByAge(age)
                .orElseThrow(() -> new NotFoundException("criminal not found..."));
        CriminalEntity criminal = getCriminalEntity(obj);

        return Ok.builder()
                .statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .date(LocalDateTime.now())
                .message(criminal)
                .build();

    }

    @Override
    public Ok<?> findCriminalByEmail(String email) throws IOException {
        CriminalEntity obj = criminalRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("criminal not found..."));

        CriminalEntity criminal = getCriminalEntity(obj);
        return Ok.builder()
                .statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .date(LocalDateTime.now())
                .message(criminal)
                .build();
    }

    @Override
    public Ok<?> findCriminalByNameAndGender(String name, String gender) throws IOException {

        CriminalEntity obj = criminalRepository.findByFirstNameAndGender(name, gender)
                .orElseThrow(() -> new NotFoundException("criminal not found..."));
        CriminalEntity criminal = getCriminalEntity(obj);

        return Ok.builder()
                .statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .date(LocalDateTime.now())
                .message(criminal)
                .build();

    }

    @Override
    public Ok<?> deleteById(Long criminalId) throws NotFoundException,
            org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException {

        s3Utility.deleteUtil(criminalId, criminalRepository);
        return Ok.builder()
                .statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .date(LocalDateTime.now())
                .message("criminal deleted...")
                .build();
    }

//    Todo redundant remove it.
    @Override
    public Ok<?> deleteByCriminalName(String email) {
        criminalRepository.deleteByEmail(email);

//        s3Utility.deleteUtil(1,criminalRepository);
        return Ok.builder()
                .statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .date(LocalDateTime.now())
                .message("criminal deleted...")
                .build();
    }

    @Override
    public Ok<?> updateById(CriminalModel criminalModel, Long criminalId,
                            MultipartFile imgFile) throws IOException {

        if(imgFile == null && criminalModel == null)throw new IllegalArgumentException("all field can not be blank");
        CriminalEntity foundCriminal = criminalRepository.findById(criminalId)
                .orElseThrow(()-> new NotFoundException("criminal not found..."));

if(criminalModel != null && criminalModel.getFirstName() != null && !criminalModel.getFirstName().isBlank()){
            foundCriminal.setFirstName(criminalModel.getFirstName());
        }
        if(criminalModel != null && criminalModel.getLastName() != null && !criminalModel.getLastName().isBlank()){
            foundCriminal.setLastName(criminalModel.getLastName());
        }
        if(criminalModel != null && criminalModel.getAddress() != null && !criminalModel.getAddress().isBlank()){
            foundCriminal.setAddress(criminalModel.getAddress());
        }
        if(criminalModel != null && criminalModel.getGender() != null && !criminalModel.getGender().isBlank() ){
            foundCriminal.setGender(criminalModel.getGender());
        }
        if(criminalModel != null && criminalModel.getEmail() != null && !criminalModel.getEmail().isBlank()){
            foundCriminal.setEmail(criminalModel.getEmail());
        }
        if(criminalModel != null && criminalModel.getContactNumber() != null && !criminalModel.getContactNumber().isBlank()){
            foundCriminal.setContactNumber(criminalModel.getContactNumber());
        }
        if(criminalModel != null && criminalModel.getDateOfBirth() != null && !criminalModel.getDateOfBirth().isBlank()){
            foundCriminal.setDateOfBirth(criminalModel.getDateOfBirth());
        }
        if(criminalModel != null && criminalModel.getNationality() != null && !criminalModel.getNationality().isBlank()){
            foundCriminal.setNationality(criminalModel.getNationality());
        }
        if(criminalModel != null &&  criminalModel.getTattoos() != null && !criminalModel.getTattoos().isBlank()){
            foundCriminal.setTattoos(criminalModel.getTattoos());
        }
        if(criminalModel != null && criminalModel.getEyeColor() != null &&  !criminalModel.getEyeColor().isBlank()){
            foundCriminal.setEyeColor(criminalModel.getEyeColor());
        }
        if(criminalModel != null &&  criminalModel.getHairColor() != null &&  !criminalModel.getHairColor().isBlank()){
            foundCriminal.setHairColor(criminalModel.getHairColor());
        }

        if(imgFile != null){

            UUID uuid = UUID.randomUUID();
            String imageName = "criminal-image/"+foundCriminal.getFirstName()
                    + uuid + "-" + imgFile.getOriginalFilename();
            foundCriminal.setPhotograph(imageName);
            if(foundCriminal.getPhotograph() != null) {

                amazonS3.deleteObject(new DeleteObjectRequest(bucketName, foundCriminal.getPhotograph()));
                s3Utility.saveToAws(imgFile.getInputStream(), imageName);

            }
        }
        if(criminalModel != null &&  criminalModel.getScars() != null &&  !criminalModel.getScars().isBlank()){
            foundCriminal.setScars(criminalModel.getScars());
        }

        if(criminalModel != null && criminalModel.getWeight() != null){
            foundCriminal.setWeight(criminalModel.getWeight());
        }

        if(criminalModel != null &&  criminalModel.getAge() != null){
            foundCriminal.setAge(criminalModel.getAge());
        }



        criminalRepository.save(foundCriminal);

        return Ok.builder().statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message("criminal update successful...")
                .build();
    }


}
