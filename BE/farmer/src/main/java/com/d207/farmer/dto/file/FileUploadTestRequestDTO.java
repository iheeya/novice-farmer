package com.d207.farmer.dto.file;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileUploadTestRequestDTO {

    private MultipartFile file;
}
