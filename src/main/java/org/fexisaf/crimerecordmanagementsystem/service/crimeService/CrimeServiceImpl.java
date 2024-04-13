package org.fexisaf.crimerecordmanagementsystem.service.crimeService;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fexisaf.crimerecordmanagementsystem.entity.CrimeEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.fexisaf.crimerecordmanagementsystem.model.CrimeModel;
import org.fexisaf.crimerecordmanagementsystem.repository.CrimeRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.CriminalRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.UserRepository;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.util.S3Utility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class CrimeServiceImpl implements CrimeService {

    private final CrimeRepository crimeRepository;
    private final CriminalRepository criminalRepository;
    private final AmazonS3 amazonS3;
    @Value("${aws.bucket-name}")
    private String bucketName;
    private final UserRepository investigator;

    private final S3Utility s3Utility;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Ok<?> reportCrime(CrimeModel crimeModel,
                             MultipartFile imgFile, MultipartFile videoFile) throws IOException {

        try {

            String imagePath = null;
            String videoPath = null;

            if (imgFile != null) {
                imagePath = "crime-report/image/" + UUID.randomUUID()+
                        "-"  +imgFile.getOriginalFilename();
               s3Utility.saveToAws(imgFile.getInputStream(), imagePath);
            }

            if (videoFile != null) {
                videoPath =  "crime-report/video/" +UUID.randomUUID()+
                        "-" + videoFile.getOriginalFilename();
               s3Utility.saveToAws(videoFile.getInputStream(), videoPath);
            }


           return saveCrimeReport(crimeModel, imagePath, videoPath);
//           TODO update real Time using web socket
//           TODO handle this error PersistentObjectException and ExpiredJwtException
        } catch (IOException e) {
            throw new IOException("Error processing file upload: " + e.getMessage());
        }catch (AmazonS3Exception e){
            throw new AmazonS3Exception(e.getMessage());
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private Ok<?> saveCrimeReport(CrimeModel crimeModel,
                                 String imagePath,
                                 String videoPath) {
UserEntity foundUser = (UserEntity) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

       if(!(foundUser.getRole() == Role.LAW_ENFORCEMENT_OFFICER))
           throw new IllegalArgumentException("you cannot perform this action...");
       if (crimeModel.getDescription() == null ||
                crimeModel.getDescription().isBlank()) {
            throw new IllegalArgumentException("description cannot be empty...");
        }

        CrimeEntity crimeEntity = CrimeEntity.builder()
                .crimeDescription(crimeModel.getDescription())
                .video(videoPath)
                .image(imagePath)
                .crimeLocation(crimeModel.getLocation())
                .crimeDate(crimeModel.getDate())
                .crimeType(crimeModel.getCrimeType())
                .investigatingOfficer(foundUser)
                .build();

       crimeRepository.save(crimeEntity);

        Map<String, Object> map = new HashMap<>();
        map.put("successMessage","report successfully sent..." );
        map.put("savedData", crimeEntity);
        return Ok.builder()
                .statusCode(HttpStatus.CREATED.value())
                .statusName(HttpStatus.CREATED.name())
                .message(map)
                .date(LocalDateTime.now())
                .build();

    }


    @Override
        public Ok<?> getCrimeById(Long crimeId) throws IOException {
            //       TODO correct it this all data is been fetch instead of the crime data only
            CrimeEntity crime = crimeRepository.findById(crimeId)
                    .orElseThrow(()->new NotFoundException("crime not found..."));

        S3Utility.getImgAndVidPresignedUrl result = s3Utility.getGetImgAndVidPresignedUrl(crime.getImage(), crime.getVideo());

        CrimeEntity obj = CrimeEntity.builder()
                    .crimeId(crime.getCrimeId())
                    .crimeType(crime.getCrimeType())
                    .crimeDate(crime.getCrimeDate())
                    .investigatingOfficer(crime.getInvestigatingOfficer())
                    .crimeDescription(crime.getCrimeDescription())
                    .crimeLocation(crime.getCrimeLocation())
                    .video(result.videoUrl())
                    .image(result.imageUrl())
                    .createdAt(crime.getCreatedAt())
                    .status(crime.getStatus())
                    .build();

            return Ok.builder()
                    .statusName(HttpStatus.OK.name())
                    .statusCode(HttpStatus.OK.value())
                    .date(LocalDateTime.now())
                    .message(obj)
                    .build();
        }



    @Override
    public Ok<?> getAllByCrimeDate(String crimeDate) {
       List<CrimeEntity> obj = new ArrayList<>();
               addPresignedUrlToList(crimeRepository.findAllByCrimeDate(crimeDate), obj);

        return Ok.builder()
                .message(obj)
                .date(LocalDateTime.now())
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public Ok<?> getAllCrimeByCrimeLocation(String crimeLocation) {

        List<CrimeEntity> obj = new ArrayList<>();
                addPresignedUrlToList(crimeRepository.findAllByCrimeLocation(crimeLocation), obj);
        
        return Ok.builder()
                .message(obj)
                .date(LocalDateTime.now())
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    private void addPresignedUrlToList(List<CrimeEntity> crimeRepository, List<CrimeEntity> obj) {
        crimeRepository
                .forEach(entity -> {
                    try {
                        String imageUrl = entity.getImage() != null ? s3Utility.generatePresignedUrl(HttpMethod.GET, entity.getImage()) : null;
                        String videoUrl = entity.getVideo() != null ? s3Utility.generatePresignedUrl(HttpMethod.GET, entity.getVideo()) : null;

                        entity.setImage(imageUrl);
                        entity.setVideo(videoUrl);

                        obj.add(entity);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

    @Override
    public Ok<?> getAllCrimeByCrimeType(String crimeType) {
        List<CrimeEntity> obj = new ArrayList<>();

        addPresignedUrlToList(crimeRepository.findAllByCrimeType(crimeType), obj);
        
        return Ok.builder()
                .message(obj)
                .date(LocalDateTime.now())
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public Ok<?> getAllCrimeByInvestigator(UserEntity investigator) {

        List<CrimeEntity> obj = new ArrayList<>();
        addPresignedUrlToList(crimeRepository.findAllByInvestigatingOfficer(investigator), obj);

        return Ok.builder()
                .message(obj)
                .date(LocalDateTime.now())
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .build();
    }


    @Override
    public Ok<?> getAllCrimeReport() {
        List<CrimeEntity> allCrime = new ArrayList<>();
        addPresignedUrlToList(crimeRepository.findAll(), allCrime);

        return Ok.builder().statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message(allCrime)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Ok<?> deleteById(Long crimeId) {
            try {
                s3Utility.deleteUtil(crimeId, crimeRepository);

                return Ok.builder().statusCode(HttpStatus.OK.value())
                        .statusName(HttpStatus.OK.name())
                        .statusCode(HttpStatus.OK.value())
                        .date(LocalDateTime.now())
                        .message("crime deletion successful...")
                        .build();

            }catch (AmazonS3Exception e){
                throw new AmazonS3Exception(e.getMessage());
            } catch (org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException e) {
                throw new RuntimeException(e);
            }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Ok<?> updateById(CrimeModel crimeModel, Long crimeId,
                            MultipartFile imgFile, MultipartFile videoFile) throws IOException {

       CrimeEntity foundCrime = crimeRepository.findById(crimeId)
                .orElseThrow(()-> new NotFoundException("crime not found..."));


        if(crimeModel.getCrimeType() != null && !crimeModel.getCrimeType().isBlank()){
            foundCrime.setCrimeType(crimeModel.getCrimeType());
        }
        if(crimeModel.getDate() != null && !crimeModel.getDate().isBlank()){
            foundCrime.setCrimeDate(crimeModel.getDate());
        }
        if(crimeModel.getDescription() != null && !crimeModel.getDescription().isBlank()){
            foundCrime.setCrimeDescription(crimeModel.getDescription());
        }
        if(crimeModel.getStatus() != null && !crimeModel.getStatus().isBlank()){
            foundCrime.setStatus(crimeModel.getStatus());
        }

        if(crimeModel.getLocation() != null && !crimeModel.getLocation().isBlank()){
            foundCrime.setCrimeLocation(crimeModel.getLocation());
        }


        if(imgFile != null){

              String  imagePath = "crime-report/image/" + UUID.randomUUID()
                      + "-" +imgFile.getOriginalFilename();

            foundCrime.setImage(imagePath);
            if(foundCrime.getImage() != null) {

                amazonS3.deleteObject(
                        new DeleteObjectRequest(bucketName, foundCrime.getImage()));
                s3Utility.saveToAws(imgFile.getInputStream(), imagePath);

            }
        }
        if(videoFile != null){

            String  videoPath = "crime-report/video/" + UUID.randomUUID()
                    + "-" +videoFile.getOriginalFilename();

            foundCrime.setImage(videoPath);
            if(foundCrime.getVideo() != null) {

                amazonS3.deleteObject(
                        new DeleteObjectRequest(bucketName, foundCrime.getVideo()));
                s3Utility.saveToAws(videoFile.getInputStream(), videoPath);

            }
        }

        crimeRepository.save(foundCrime);

        return Ok.builder().statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message("crime update successful...")
                .build();

    }

}

