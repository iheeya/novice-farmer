package com.d207.farmer.dto.file;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MultiFileUploadTestRequestDTO {
    private List<MultipartFile> files; // 파일을 리스트로 변경
}
