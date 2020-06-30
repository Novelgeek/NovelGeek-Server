package lk.ucsc.NovelGeek.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.amazonaws.services.s3.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AWSS3Service  {

    private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3Service.class);

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.endpointUrl}")
    private String endPointUrl;

    // @Async annotation ensures that the method is executed in a different background thread
    // but not consume the main thread.
//    @Async
    public String uploadFile(final MultipartFile multipartFile) {
        LOGGER.info("File upload in progress.");
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            String fileName = uploadFileToS3Bucket(bucketName, file);
            LOGGER.info("File upload is completed.");
            file.delete();  // To remove the file locally created in the project folder.
            return endPointUrl + "/" + fileName;
        } catch (final AmazonServiceException ex) {
            LOGGER.info("File upload is failed.");
            LOGGER.error("Error= {} while uploading file.", ex.getMessage());
        }
        return null;
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            LOGGER.error("Error converting the multi-part file to file= ", ex.getMessage());
        }
        return file;
    }

    private String uploadFileToS3Bucket(final String bucketName, final File file) {
        final String uniqueFileName = new Date().getTime() + "_" + file.getName().replace(" ", "_");
        LOGGER.info("Uploading file with name= " + uniqueFileName);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
        PutObjectResult result= amazonS3.putObject(putObjectRequest);
        return uniqueFileName;
    }
}