package com.fictiontimes.fictiontimesbackend.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtils {

    private static AmazonS3 s3Client = null;

    public static String saveFile(Part partFile, String path) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(partFile.getSize());
        metadata.setContentType(partFile.getContentType());
        String fileName = partFile.getSubmittedFileName().replaceAll(" ", "-");
        PutObjectRequest request = new PutObjectRequest(
                "fiction-times-bucket",
                path + "-" + fileName,
                partFile.getInputStream(),
                metadata
        );
        getAWSClient().putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
        return "https://fiction-times-bucket.s3.ap-south-1.amazonaws.com/" + path + "-" + fileName;
    }

    public static void deleteFile(String fileUrl) throws IOException {
        String key = fileUrl.replace("https://fiction-times-bucket.s3.ap-south-1.amazonaws.com/", "");
        getAWSClient().deleteObject("fiction-times-bucket", key);
    }

    public static String saveEpub(Part partFile, int storyId, int episodeNumber) throws IOException {
        String content = "";
        if (partFile.getSubmittedFileName().endsWith(".epub")) {
            // Make the temp zip file
            InputStream inputStream = partFile.getInputStream();
            File tempFile = new File("tempfile.zip");
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
            ZipFile epub = new ZipFile(tempFile);
            // Loop through the zip entries
            Enumeration<? extends ZipEntry> entries = epub.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.endsWith(".xhtml") && !name.endsWith("nav.xhtml")) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(epub.getInputStream(entry)));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        content += line;
                    }
                    bufferedReader.close();
                } else if (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg")) {
                    String fileName = storyId + "-" + episodeNumber + "-" + name.split("/")[2];
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(entry.getSize());
                    PutObjectRequest request = new PutObjectRequest(
                            "fiction-times-bucket",
                            "images/" + fileName,
                            epub.getInputStream(entry),
                            metadata
                    );
                    getAWSClient().putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
                }
            }
        }
        content = content.replaceAll(
                "images/",
                "https://fiction-times-bucket.s3.ap-south-1.amazonaws.com/images/"
                        + storyId + "-" + episodeNumber + "-"
        );
        Pattern pattern = Pattern.compile("<body(.+?)</body>");
        Matcher matcher = pattern.matcher(content);
        matcher.find();
        String body = "<div" + matcher.group(1) + "</div>";
        pattern = Pattern.compile("<style type=\"text/css\">(.+?)</style>");
        matcher = pattern.matcher(content);
        matcher.find();
        String styles = "<style type=\"text/css\">" + matcher.group(1) + "</style>";
        content = styles + "\n" + body;
        return content;
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
