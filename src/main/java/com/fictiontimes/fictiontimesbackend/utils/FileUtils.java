package com.fictiontimes.fictiontimesbackend.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.Properties;

public class FileUtils {

    private static AmazonS3 s3Client = null;

    public static String saveFile(Part partFile) throws IOException {
        String fileName = partFile.getSubmittedFileName().replaceAll(" ", "-");
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(partFile.getSize());
        metadata.setContentType(partFile.getContentType());
        PutObjectRequest request = new PutObjectRequest(
                "fiction-times-bucket",
                "files/" + fileName,
                partFile.getInputStream(),
                metadata
        );
        getAWSClient().putObject(request);
        return "https://fiction-times-bucket.s3.ap-south-1.amazonaws.com/files/" + fileName;
    }

    private static AmazonS3 getAWSClient() throws IOException {
        // Load properties
        if (s3Client == null) {
            Properties properties = new Properties();
            properties.load(FileUtils.class.getResourceAsStream("/awsS3.properties"));
            String accessKeyID = properties.getProperty("aws.access.key.id");
            String secretAccessKey = properties.getProperty("aws.secret.access.key");

            BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyID, secretAccessKey);
            s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.AP_SOUTH_1)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();
            return s3Client;
        }
        return s3Client;
    }
}
