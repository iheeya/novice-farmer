package com.d207.farmer.dto.myplant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InspectionPestRequestDTO {
    private Long farmId;
    private MultipartFile file;
}
