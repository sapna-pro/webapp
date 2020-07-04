package com.webapp.assignment.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


@Service
public class AmazonClient {


    private AmazonS3 s3client;

    @Value("${aws.endpointUrl}")
    private String endpointUrl;
    @Value("${aws.bucket.name}")
    private String bucketName;
    @Value("${aws.access.key.id}")
    private String accessKey;
    @Value("${aws.access.key.secret}")
    private String secretKey;


    private Logger logger = LoggerFactory.getLogger(AmazonClient.class);

    @PostConstruct
    private void initializeAmazon() {

        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .build();
        logger.info("client created");


//        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//        this.s3client = new AmazonS3Client(credentials);
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {

        String fileUrl = "";
        try {
            logger.info("Inside try of upload");
            File file = convertMultiPartToFile(multipartFile);
            file.canWrite();
            file.canRead();
            String fileName = generateFileName(multipartFile);
            fileUrl = "https://s3.amazonaws.com" + "/" + bucketName + "/" + fileName;
            logger.info("File URL is " + fileUrl);
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } /*catch (Exception e) {
			e.printStackTrace();
		}*/
        catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());


        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (IOException ioe) {
            logger.info("IOE Error Message: " + ioe.getMessage());

        }
        return fileUrl;

    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        System.out.println(fileName + "filename");
        s3client.deleteObject(new DeleteObjectRequest(bucketName,fileName));
        return "Successfully deleted";
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(
                new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        //.withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


}
