package com.d207.farmer.dto.myplant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InspectionPestRequestDTO {
    private Long farmId;
    private MultipartFile file;
}
