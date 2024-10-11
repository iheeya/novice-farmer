package com.d207.farmer.controller.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.d207.farmer.dto.common.FileDirectory;
import com.d207.farmer.dto.file.FileUploadTestRequestDTO;
import com.d207.farmer.utils.FileUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Tag(name = "파일관리", description = "file")
public class FileController {

    private final AmazonS3Client amazonS3Client;
    private final FileUtil fileUtil;
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(fileUtil.uploadFile(file, FileDirectory.PLACE));
    }

    @GetMapping
    public ResponseEntity<String> downloadFile(@RequestParam("fileName") String fileName) {
        try {
            S3Object s3Object = amazonS3Client.getObject(bucket, fileName);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();

            byte[] content = IOUtils.toByteArray(inputStream);
            return ResponseEntity.ok().body(Arrays.toString(content));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/test")
    public ResponseEntity<String> testUploadFile(@ModelAttribute FileUploadTestRequestDTO request) {
        log.info("Received Test Upload file ");
        return ResponseEntity.ok().body(fileUtil.uploadFile(request.getFile(), FileDirectory.TEST));
    }
}
