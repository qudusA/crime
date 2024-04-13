package org.fexisaf.crimerecordmanagementsystem.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class S3Utility {

    private final AmazonS3 amazonS3;

    @Value("${aws.bucket-name}")
    private String bucketName;


    public void saveToAws(InputStream inputStream, String fileName) throws IOException {
        try {


                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(getContentType(fileName));
                objectMetadata.setContentLength(inputStream.available());

            amazonS3.putObject(bucketName, fileName, inputStream, objectMetadata);
        } catch (AmazonS3Exception e) {
            throw new AmazonS3Exception(e.getMessage());
        }
    }

    private String getContentType(String fileName) {
        String extension = getFileExtension(fileName);
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "pdf" -> "application/pdf";
            case "mp4" -> "video/mp4";
//            case "webm" -> "video/webm";
//            case "avi" -> "video/x-msvideo";
//            case "mov" -> "video/quicktime";
//            case "mkv" -> "video/x-matroska";
//            case "flv" -> "video/x-flv";
            default -> "application/octet-stream";
        };
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return ""; // No file extension found or file name ends with a dot
        }
        return fileName.substring(lastDotIndex + 1);
    }




    public String generatePresignedUrl(HttpMethod httpMethod, String fileName) throws IOException {
        try {

            Date date = new Date(System.currentTimeMillis() + 1000 * 60);

            return amazonS3.generatePresignedUrl(bucketName, fileName, date, httpMethod)
                    .toString();

        }catch (AmazonS3Exception e){
            throw new AmazonS3Exception(e.getMessage());
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }


    public getImgAndVidPresignedUrl getGetImgAndVidPresignedUrl(String image, String video) throws IOException {
        var imageUrl = image == null ? null : generatePresignedUrl(HttpMethod.GET, image);
        var videoUrl = video == null ? null : generatePresignedUrl(HttpMethod.GET, video);
        return new getImgAndVidPresignedUrl(imageUrl, videoUrl);
    }

    public record getImgAndVidPresignedUrl(String imageUrl, String videoUrl) {
    }



    public <T> void deleteUtil(Long id, JpaRepository<T, Long> crimeRepository) throws NotFoundException {
        Optional<T> foundEntity = crimeRepository.findById(id);
        if (foundEntity.isPresent()) {
            T deleted = foundEntity.get();

            try {
                Method getImageMethod = deleted.getClass().getMethod("getImage");
                Method getVideoMethod = deleted.getClass().getMethod("getVideo");

                String image = (String) getImageMethod.invoke(deleted);
                String video = (String) getVideoMethod.invoke(deleted);

                if (image != null) {
                    amazonS3.deleteObject(new DeleteObjectRequest(bucketName, image));
                }
                if (video != null) {
                    amazonS3.deleteObject(new DeleteObjectRequest(bucketName, video));
                }

                crimeRepository.deleteById(id);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {

                throw new RuntimeException("Error accessing entity properties", e);
            }
        } else {
            throw new NotFoundException("Entity not found with id: " + id);
        }
    }



}
