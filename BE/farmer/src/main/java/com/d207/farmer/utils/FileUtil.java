package com.d207.farmer.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.d207.farmer.dto.common.FileDirectory;
import com.d207.farmer.exception.MyFileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
//@Component
@RequiredArgsConstructor
public class FileUtil {

    private final AmazonS3Client amazonS3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file, FileDirectory directory) {
        if(file.isEmpty()) return null;
        String originFileName = file.getOriginalFilename();
        String uploadFileName = createUploadFileName(originFileName);

//        String fileUrl = "https://" + bucket + directory.toString().toLowerCase();
        String filePrefix = directory.toString().toLowerCase() + "/";
        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentType(file.getContentType());
        metaData.setContentLength(file.getSize());
        try {
            amazonS3Client.putObject(bucket, filePrefix + uploadFileName, file.getInputStream(), metaData);
            return uploadFileName;
        } catch (IOException e) {
            throw new MyFileUploadException("파일 업로드에 실패하였습니다.", e.getCause());
        }
    }

    private String createUploadFileName(String originFileName) {
        String ext = extractExt(originFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originFileName) {
        int pos = originFileName.lastIndexOf(".");
        return originFileName.substring(pos+1);
    }

}
