package com.d207.farmer.dto.user.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class UserMypageHistoryResponseDTO {

    Long id;
    String plantname; ///Plant plant   ->
    String plantmyname;
    String placename; //UserPlace userPlace -> S
    LocalDateTime seedDate;
    LocalDateTime firstHarvestDate;
    String imageurl;// //내 Farm 테이블에 존재하는 farm_growth_step의 값을 통해  Plant -> PlantGrowthillust 에서 가져오고 싶어


}
