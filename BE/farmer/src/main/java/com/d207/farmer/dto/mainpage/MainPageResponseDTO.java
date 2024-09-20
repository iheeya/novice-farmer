package com.d207.farmer.dto.mainpage;

import com.d207.farmer.dto.mainpage.components.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MainPageResponseDTO {
    TodoInfoComponentDTO todoInfo;
    BeginnerInfoComponentDTO beginnerInfo;
    MyFarmListInfoComponentDTO myFarmListInfo;
    FarmGuideInfoComponentDTO farmGuideInfo;
    FavoritesInfoComponentDTO favoritesInfo;
    MyPlantInfoComponentDTO myPlantInfo;
    RecommendInfoComponentDTO recommendInfo;
    CommunityInfoComponentDTO communityInfo;
    WeekendFarmComponentDTO weekendFarm;
}
