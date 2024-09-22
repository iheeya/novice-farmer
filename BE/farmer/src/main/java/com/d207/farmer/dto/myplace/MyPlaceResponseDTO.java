package com.d207.farmer.dto.myplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MyPlaceResponseDTO {
    private PlaceInfoDTO placeInfo;
    private List<MyPlaceFarmDTO> farms;
}
