package com.d207.farmer.utils;

import com.d207.farmer.domain.common.Address;
import com.d207.farmer.domain.community.*;
import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.FarmTodo;
import com.d207.farmer.domain.farm.TodoType;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.plant.PlantGrowthIllust;
import com.d207.farmer.domain.plant.PlantThreshold;
import com.d207.farmer.domain.user.Gender;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.farm.register.FarmPlaceRegisterDTO;
import com.d207.farmer.dto.farm.register.FarmPlantRegisterDTO;
import com.d207.farmer.dto.farm.register.FarmRegisterRequestDTO;
import com.d207.farmer.dto.myplant.ManagePlantRequestDTO;
import com.d207.farmer.dto.myplant.StartGrowPlantRequestDTO;
import com.d207.farmer.dto.place.PlaceRegisterRequestDTO;
import com.d207.farmer.dto.plant.PlantRegisterRequestDTO;
import com.d207.farmer.dto.survey.SurveyRegisterRequestDTO;
import com.d207.farmer.dto.user.UserRegisterRequestDTO;
import com.d207.farmer.dto.weekend_farm.WeekendFarmRegisterRequestDTO;
import com.d207.farmer.repository.community.CommunityHeartRepository;
import com.d207.farmer.repository.community.CommunityRepository;
import com.d207.farmer.repository.community.CommunitySelectedTagRespository;
import com.d207.farmer.repository.community.CommunityTagRepository;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.farm.FarmTodoRepository;
import com.d207.farmer.repository.mainpage.CommunityFavoriteTagForMainPageRepository;
import com.d207.farmer.repository.plant.PlantIllustRepository;
import com.d207.farmer.repository.plant.PlantRepository;
import com.d207.farmer.repository.plant.PlantThresholdRepository;
import com.d207.farmer.repository.user.UserRepository;
import com.d207.farmer.service.common.SampleService;
import com.d207.farmer.service.farm.FarmService;
import com.d207.farmer.service.place.PlaceService;
import com.d207.farmer.service.plant.PlantService;
import com.d207.farmer.service.user.MyPlantService;
import com.d207.farmer.service.user.UserService;
import com.d207.farmer.service.weekend_farm.WeekendFarmService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupApplicationListener {

    private static final int USER_NUM = 10;
    private final Object[][] plantSamples = {{"토마토", 1980, true}, {"고추", 2000, true}, {"상추", 2000, false}, {"깻잎", 2000, false}, {"배추", 2000, false}};
    private final Object[][] placeSamples = {{"베란다", "베란다 접근성 미쳤고", true}, {"주말농장", "주말농장 재밌음", true}, {"개인 텃밭", "개인 땅이라니 부럽다", false}, {"스쿨팜", "잼민이 주의", false}};
    private final String[][] weekendFarmSamples = {
            {"쭌이네, BINIL HAUS", "경북 구미시 산동읍 성수4길 65 쭌이네비닐하우스", "0507-1317-4536", "36.1573359", "128.4209952", "https://image.com/fdf", "성수리 현대정비에서 산길따라~~쭈우쭈욱 올라오세요^^ (현대정비를 왼쪽에두고 굴다리지남^^)"},
            {"베리마토 딸기농장", "경북 구미시 지산양호9길 10", "010-2600-5603", "36.1358311", "128.3632623", "https://blog.naver.com/seog1017", "최원석, 배성연 부부가  땀으로 정직하게 농사짓고 있는  딸기, 토마토 농장 베리마토 입니다.   판매상품 당일 수확한 딸기 딸기 체험 주말 가족체험"},
    };

    private final UserService userService;
    private final SampleService sampleService;
    private final PlantService plantService;
    private final PlaceService placeService;
    private final WeekendFarmService weekendFarmService;
    private final PlantIllustRepository plantIllustRepository;
    private final PlantThresholdRepository plantThresholdRepository;
    private final FarmService farmService;
    private final MyPlantService myPlantService;
    private final FarmTodoRepository farmTodoRepository;
    private final FarmRepository farmRepository;
    private final PlantRepository plantRepository;
    private final CommunityTagRepository communityTagRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CommunityHeartRepository communityHeartRepository;
    private final CommunitySelectedTagRespository communitySelectedTagRespository;
    private final CommunityFavoriteTagForMainPageRepository communityFavoriteTagRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Application ready");
       createPlantSample();
       createPlaceSample();
       createWeekendFarmSample();
       createPlantIllustSample();
       createPlantThresholdSample();
       createUserSample();
       createFarmSample();
       createTodoSample();
       createCommunityTagSample();
       createCommunitySample();
    }

    private void createUserSample() {
        for (int i = 1; i < USER_NUM + 1; i++) {
            UserRegisterRequestDTO u = new UserRegisterRequestDTO(
                    "test" + i + "@email.com", "1234", "test" + i, 20 + i, Gender.MALE, "대구광역시 달서구", true
            );
            sampleService.registerUser(u);
        }
        UserRegisterRequestDTO u1 = new UserRegisterRequestDTO( // id 11번
                "farm1@email.com", "1234", "farmer1", 25, Gender.MALE, "경상북도 구미시", true
        );
        User user = sampleService.registerUser(u1);

        // 설문조사 추가(farm1@email.com)
        SurveyRegisterRequestDTO.Place surveyPlace = new SurveyRegisterRequestDTO.Place(1L);
        SurveyRegisterRequestDTO.Plant surveyPlant = new SurveyRegisterRequestDTO.Plant(1L);
        List<SurveyRegisterRequestDTO.Place> surveyPlaces = new ArrayList<>();
        List<SurveyRegisterRequestDTO.Plant> surveyPlants = new ArrayList<>();
        surveyPlaces.add(surveyPlace);
        surveyPlants.add(surveyPlant);
        sampleService.registerFavorites(user, new SurveyRegisterRequestDTO(surveyPlants, surveyPlaces));

        UserRegisterRequestDTO u2 = new UserRegisterRequestDTO(
                "farm2@email.com", "1234", "farmer2", 25, Gender.MALE, "경상북도 구미시", true
        );
        sampleService.registerUser(u2);
        UserRegisterRequestDTO u3 = new UserRegisterRequestDTO(
                "mainpage@email.com", "1234", "mainpage", 30, Gender.MALE, "경상북도 포항시", true
        );
        sampleService.registerUser(u3);
    }

    private void createPlantSample() {
        for (int i = 0; i < plantSamples.length; i++) {
            plantService.registerPlant(new PlantRegisterRequestDTO(plantSamples[i][0].toString(), (Integer)plantSamples[i][1], (Boolean)plantSamples[i][2]));
        }
    }

    private void createPlaceSample() {
        for (int i = 0; i < placeSamples.length; i++) {
            placeService.registerPlace(new PlaceRegisterRequestDTO(placeSamples[i][0].toString(), placeSamples[i][1].toString(), (Boolean) placeSamples[i][2]));
        }
    }

    private void createWeekendFarmSample() {
        for (int i = 0; i < weekendFarmSamples.length; i++) {
            weekendFarmService.registerWeekendFarm(new WeekendFarmRegisterRequestDTO(weekendFarmSamples[i][0], weekendFarmSamples[i][1], weekendFarmSamples[i][2],
                    weekendFarmSamples[i][3], weekendFarmSamples[i][4], weekendFarmSamples[i][5], weekendFarmSamples[i][6]));
        }
    }

    private void createPlantIllustSample() {
        List<Plant> plants = plantRepository.findAll();
        for (Plant plant : plants) {
            for (int i = 0; i < 4; i++) {
                plantIllustRepository.save(new PlantGrowthIllust(plant, i, "sample illust image path for " + plant.getName() + " " + i + "단계"));
            }
        }
    }

    private void createPlantThresholdSample() {
        List<Plant> plants = plantRepository.findAll();
        for (Plant plant : plants) {
            plantThresholdRepository.save(new PlantThreshold(plant, 1, 300));
            plantThresholdRepository.save(new PlantThreshold(plant, 2, 1000));
            plantThresholdRepository.save(new PlantThreshold(plant, 3, 1500));
        }
    }

    private void createFarmSample() {
        Address address = new Address("경북", "구미시", "", "임수동", null, "경북 구미시 임수동 94-1", "39388");

        FarmPlaceRegisterDTO farmPlace1 = new FarmPlaceRegisterDTO(1L, address);
        FarmPlantRegisterDTO farmPlant1 = new FarmPlantRegisterDTO(1L, "토순이", "토마토 냠냠");
        FarmRegisterRequestDTO farmRegister1 = new FarmRegisterRequestDTO(farmPlace1, farmPlant1);
        farmService.registerFarm(11L, farmRegister1);
        myPlantService.startGrowPlant(11L, new StartGrowPlantRequestDTO(1L));

        Farm farm = farmRepository.findById(1L).orElseThrow();
        farm.updateDegreeDay(1000);

        FarmPlaceRegisterDTO farmPlace2 = new FarmPlaceRegisterDTO(2L, address);
        FarmPlantRegisterDTO farmPlant2 = new FarmPlantRegisterDTO(2L, "작매고", "작은 고추가 매움");
        FarmRegisterRequestDTO farmRegister2 = new FarmRegisterRequestDTO(farmPlace2, farmPlant2);
        farmService.registerFarm(11L, farmRegister2);
        myPlantService.startGrowPlant(11L, new StartGrowPlantRequestDTO(2L));
        myPlantService.harvestPlant(11L, new ManagePlantRequestDTO(2L));
        myPlantService.endPlant(11L, new ManagePlantRequestDTO(2L));

        FarmPlaceRegisterDTO farmPlace3 = new FarmPlaceRegisterDTO(3L, address);
        FarmPlantRegisterDTO farmPlant3 = new FarmPlantRegisterDTO(1L, "상충", "쌈쌈");
        FarmRegisterRequestDTO farmRegister3 = new FarmRegisterRequestDTO(farmPlace3, farmPlant3);
        farmService.registerFarm(11L, farmRegister3);
        myPlantService.startGrowPlant(11L, new StartGrowPlantRequestDTO(3L));

    }

    private void createTodoSample() {
        Farm farm = farmRepository.findById(1L).orElseThrow();
        farmTodoRepository.save(new FarmTodo(farm, TodoType.WATERING, false, LocalDateTime.now().plusDays(1), null));
        farmTodoRepository.save(new FarmTodo(farm, TodoType.FERTILIZERING, false, LocalDateTime.now().plusDays(6), null));
    }

    private void createCommunityTagSample() {
        String[] tagNames = {"토마토", "고추", "상추", "깻잎", "배추", "베란다", "주말농장", "개인 텃밭", "스쿨팜"};
        for (String tagName : tagNames) {
            communityTagRepository.save(new CommunityTag(tagName));
        }
    }

    private void createCommunitySample() {
        User user = userRepository.findById(11L).orElseThrow();
        CommunityTag communityTag = communityTagRepository.findById(1L).orElseThrow();
        
        // 선호 태그
        CommunityFavoriteTag communityFavoriteTag = new CommunityFavoriteTag();
        communityFavoriteTag.setUser(user);
        communityFavoriteTag.setCommunitytag(communityTag);
        communityFavoriteTagRepository.save(communityFavoriteTag);

        String content = "토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다";
        Community community1 = communityRepository.save(new Community(user, "토마토는 맛있다1", content));
        communitySelectedTagRespository.save(new CommunitySelectedTag(community1, communityTag));
        communityHeartRepository.save(new CommunityHeart(community1, user));

        Community community2 = communityRepository.save(new Community(user, "토마토는 맛있다2", content));
        communitySelectedTagRespository.save(new CommunitySelectedTag(community2, communityTag));
        for (int i = 0; i < 2; i++) {
            communityHeartRepository.save(new CommunityHeart(community2, user));
        }

        Community community3 = communityRepository.save(new Community(user, "토마토는 맛있다3", content));
        communitySelectedTagRespository.save(new CommunitySelectedTag(community3, communityTag));
        for (int i = 0; i < 3; i++) {
            communityHeartRepository.save(new CommunityHeart(community3, user));
        }

        Community community4 = communityRepository.save(new Community(user, "토마토는 맛있다4", content));
        communitySelectedTagRespository.save(new CommunitySelectedTag(community4, communityTag));
        for (int i = 0; i < 4; i++) {
            communityHeartRepository.save(new CommunityHeart(community4, user));
        }

        Community community5 = communityRepository.save(new Community(user, "토마토는 맛있다5", content));
        communitySelectedTagRespository.save(new CommunitySelectedTag(community5, communityTag));
        for (int i = 0; i < 5; i++) {
            communityHeartRepository.save(new CommunityHeart(community5, user));
        }
    }
}
