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
import com.d207.farmer.dto.user.sample.UserSampleRegisterRequestDTO;
import com.d207.farmer.dto.utils.OnlyId;
import com.d207.farmer.dto.weekend_farm.WeekendFarmRegisterRequestDTO;
import com.d207.farmer.repository.community.*;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Boolean.TRUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupApplicationListener {

    private static final int USER_NUM = 10;
    private final String[] userNicknames = {"불꽃토마토", "농슐랭3스타", "농사하는돌아이", "농부카세", "농부여신", "농사대가", "주말엔농부", "당근조아", "토마토조아", "청양고추조아"};
    private final Object[][] plantSamples = {{"토마토", 1960, true}, {"고추", 1960, true}, {"옥수수", 1960, false}, {"오이", 1960, false}, {"콩", 1960, false},
                                             {"가지", 1960, true}, {"무", 1960, true}, {"상추", 1960, false}, {"배추", 1960, false}, {"감자", 1960, false},
                                             {"고구마", 1960, true}, {"대파", 1960, true}};
    private final Object[][] placeSamples = {{"베란다", "베란다는 베란다입니다", true}, {"주말농장", "주말농장은 주말농장입니다.", true}, {"개인텃밭", "개인텃밭은 개인텃밭입니다.", false}, {"스쿨팜", "스쿨팜은 스쿨팜입니다.", false}};
    private final String[][] weekendFarmSamples = {
            {"쭌이네, BINIL HAUS", "경북 구미시 산동읍 성수4길 65 쭌이네비닐하우스", "0507-1317-4536", "36.1573359", "128.4209952", "https://image.com/fdf", "성수리 현대정비에서 산길따라~~쭈우쭈욱 올라오세요^^ (현대정비를 왼쪽에두고 굴다리지남^^)"},
            {"베리마토 딸기농장", "경북 구미시 지산양호9길 10", "010-2600-5603", "36.1358311", "128.3632623", "https://blog.naver.com/seog1017", "최원석, 배성연 부부가  땀으로 정직하게 농사짓고 있는  딸기, 토마토 농장 베리마토 입니다.   판매상품 당일 수확한 딸기 딸기 체험 주말 가족체험"},
            {"소편한농장", "경북 구미시 고아읍 외예리 406-1", "0507-1353-3070", "36.1837223", "128.2764442", "", ""},
            {"농부의정원", "경북 구미시 무을면 수다사길 96", "0507-1348-8721", "36.2762526", "128.1654738", "http://instagram.com/farmer_garden_", "키즈 체험 농장입니다 농자니아 체험 운영 중입니다"},
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
    private final CommunityCommentRepository communityCommentRepository;

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
            UserSampleRegisterRequestDTO u = new UserSampleRegisterRequestDTO(
                    "test" + i + "@email.com", "1234", userNicknames[i-1], 20 + i, Gender.MALE,
                    "경상북도 구미시", true, userNicknames[i-1] + ".png"
            );
            sampleService.registerUser(u);
        }
        UserSampleRegisterRequestDTO u1 = new UserSampleRegisterRequestDTO( // id 11번
                "farm1@email.com", "1234", "내손이호미", 25, Gender.MALE,
                "경상북도 구미시", true, "farmer1.png"
        );
        User user = sampleService.registerUser(u1);

        // 설문조사 추가(farm1@email.com)
        OnlyId surveyPlace = new OnlyId(1L);
        OnlyId surveyPlant = new OnlyId(1L);
        List<OnlyId> surveyPlaces = new ArrayList<>();
        List<OnlyId> surveyPlants = new ArrayList<>();
        surveyPlaces.add(surveyPlace);
        surveyPlants.add(surveyPlant);
        sampleService.registerFavorites(user, new SurveyRegisterRequestDTO(surveyPlants, surveyPlaces));

        UserSampleRegisterRequestDTO u2 = new UserSampleRegisterRequestDTO(
                "farm2@email.com", "1234", "귀농각", 25, Gender.MALE,
                "경상북도 구미시", true, "farmer2.png"
        );
        sampleService.registerUser(u2);
        UserSampleRegisterRequestDTO u3 = new UserSampleRegisterRequestDTO(
                "mainpage@email.com", "1234", "농부의정원", 30, Gender.MALE,
                "경상북도 구미시", true, "mainpage.png"
        );
        User mainpageUser = sampleService.registerUser(u3);
        sampleService.registerFavorites(mainpageUser, new SurveyRegisterRequestDTO(surveyPlants, surveyPlaces));
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
        boolean isThirdStep = false;
        for (Plant plant : plants) {
            int n = isThirdStep ? 4 : 5;
            for (int i = 1; i < n; i++) {
                plantIllustRepository.save(new PlantGrowthIllust(plant, i, "sample illust image path for " + plant.getName() + " " + i + "단계"));
            }
            if(plant.getName().equals("가지")) isThirdStep = true;
        }
    }

    private void createPlantThresholdSample() {
        List<Plant> plants = plantRepository.findAll();
        boolean isThirdStep = false;
        for (Plant plant : plants) {
            if(isThirdStep) {
                plantThresholdRepository.save(new PlantThreshold(plant, 1, 1000));
                plantThresholdRepository.save(new PlantThreshold(plant, 2, 1960));
            } else {
                plantThresholdRepository.save(new PlantThreshold(plant, 1, 510));
                plantThresholdRepository.save(new PlantThreshold(plant, 2, 1060));
                plantThresholdRepository.save(new PlantThreshold(plant, 3, 1960));
            }
            if(plant.getName().equals("가지")) isThirdStep = true;
        }
    }

    private void createFarmSample() {
        Address address = new Address("경북", "구미시", "", "임수동", null, "경북 구미시 임수동 94-1", "39388");

        FarmPlaceRegisterDTO farmPlace1 = new FarmPlaceRegisterDTO(1L, address);
        FarmPlantRegisterDTO farmPlant1 = new FarmPlantRegisterDTO(1L, "토순이", "토마토 냠냠");
        FarmRegisterRequestDTO farmRegister1 = new FarmRegisterRequestDTO(farmPlace1, farmPlant1);
        Farm farm1 = farmService.registerFarm(11L, farmRegister1);
        farm1.startGrow();

        Farm farm = farmRepository.findById(1L).orElseThrow();
        farm.updateDegreeDay(1000);

        FarmPlaceRegisterDTO farmPlace2 = new FarmPlaceRegisterDTO(2L, address);
        FarmPlantRegisterDTO farmPlant2 = new FarmPlantRegisterDTO(2L, "작매고", "작은 고추가 매움");
        FarmRegisterRequestDTO farmRegister2 = new FarmRegisterRequestDTO(farmPlace2, farmPlant2);
        Farm farm2 = farmService.registerFarm(11L, farmRegister2);
        farm2.startGrow();
        myPlantService.harvestPlant(11L, new ManagePlantRequestDTO(2L));
        myPlantService.endPlant(11L, new ManagePlantRequestDTO(2L));

        FarmPlaceRegisterDTO farmPlace3 = new FarmPlaceRegisterDTO(3L, address);
        FarmPlantRegisterDTO farmPlant3 = new FarmPlantRegisterDTO(1L, "상충", "쌈쌈");
        FarmRegisterRequestDTO farmRegister3 = new FarmRegisterRequestDTO(farmPlace3, farmPlant3);
        Farm farm3 = farmService.registerFarm(11L, farmRegister3);
        farm3.startGrow();

        // mainpage 계정용
        Farm farm4 = farmService.registerFarm(13L, farmRegister1);
        Farm farm5 = farmService.registerFarm(13L, farmRegister2);

        farm4.startGrow();
        farm5.startGrow();

        farmRepository.findById(4L).orElseThrow().updateDegreeDay(1000);
        farmRepository.findById(5L).orElseThrow().updateDegreeDay(1000);
    }

    private void createTodoSample() {
        Farm farm = farmRepository.findById(1L).orElseThrow();
        farmTodoRepository.save(new FarmTodo(farm, TodoType.WATERING, "", false, LocalDateTime.now().plusDays(1), null));
        farmTodoRepository.save(new FarmTodo(farm, TodoType.FERTILIZERING, "", false, LocalDateTime.now().plusDays(6), null));
        farmTodoRepository.save(new FarmTodo(farm, TodoType.WATERING, "", true, LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(2)));
        farmTodoRepository.save(new FarmTodo(farm, TodoType.FERTILIZERING,"", true, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5)));

        // mainpage 용
        Farm farmMainPage1 = farmRepository.findById(4L).orElseThrow();
        Farm farmMainPage2 = farmRepository.findById(5L).orElseThrow();
        farmTodoRepository.save(new FarmTodo(farmMainPage1, TodoType.WATERING, "", false, LocalDateTime.now().plusDays(1), null));
        farmTodoRepository.save(new FarmTodo(farmMainPage1, TodoType.FERTILIZERING, "", false, LocalDateTime.now().plusDays(6), null));
        farmTodoRepository.save(new FarmTodo(farmMainPage1, TodoType.NATURE, "폭우주의보", false, LocalDateTime.now(), null));

        farmTodoRepository.save(new FarmTodo(farmMainPage2, TodoType.WATERING, "", false, LocalDateTime.now().plusDays(1), null));
        farmTodoRepository.save(new FarmTodo(farmMainPage2, TodoType.FERTILIZERING, "", false, LocalDateTime.now().plusDays(6), null));
        farmTodoRepository.save(new FarmTodo(farmMainPage2, TodoType.NATURE, "호우주의보", false, LocalDateTime.now(), null));

    }

    private void createCommunityTagSample() {
        String[] tagNames = {"토마토", "고추", "옥수수", "오이", "콩", "가지", "무", "상추", "배추", "감자", "고구마", "대파", "베란다", "주말농장", "개인텃밭", "스쿨팜"};
        for (String tagName : tagNames) {
            communityTagRepository.save(new CommunityTag(tagName));
        }
    }

//    private void createCommunitySample() {
//        User user = userRepository.findById(11L).orElseThrow();
//        CommunityTag communityTag = communityTagRepository.findById(1L).orElseThrow();
//
//        // 선호 태그
//        CommunityFavoriteTag communityFavoriteTag = new CommunityFavoriteTag();
//        communityFavoriteTag.setUser(user);
//        communityFavoriteTag.setCommunityTag(communityTag);
//        communityFavoriteTagRepository.save(communityFavoriteTag);
//
//        String content = "토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다";
//        Community community1 = communityRepository.save(new Community(user, "토마토는 맛있다1", content));
//        communitySelectedTagRespository.save(new CommunitySelectedTag(community1, communityTag));
//        communityHeartRepository.save(new CommunityHeart(community1, user));
//        communityCommentRepository.save(new CommunityComment(community1, user, "토마토마토마토마토1"));
//
//        Community community2 = communityRepository.save(new Community(user, "토마토는 맛있다2", content));
//        communitySelectedTagRespository.save(new CommunitySelectedTag(community2, communityTag));
//        for (int i = 0; i < 2; i++) {
//            communityHeartRepository.save(new CommunityHeart(community2, user));
//        }
//        communityCommentRepository.save(new CommunityComment(community2, user, "토마토마토마토마토2"));
//
//        Community community3 = communityRepository.save(new Community(user, "토마토는 맛있다3", content));
//        communitySelectedTagRespository.save(new CommunitySelectedTag(community3, communityTag));
//        for (int i = 0; i < 3; i++) {
//            communityHeartRepository.save(new CommunityHeart(community3, user));
//        }
//        communityCommentRepository.save(new CommunityComment(community3, user, "토마토마토마토마토3"));
//
//        Community community4 = communityRepository.save(new Community(user, "토마토는 맛있다4", content));
//        communitySelectedTagRespository.save(new CommunitySelectedTag(community4, communityTag));
//        for (int i = 0; i < 4; i++) {
//            communityHeartRepository.save(new CommunityHeart(community4, user));
//        }
//        communityCommentRepository.save(new CommunityComment(community4, user, "토마토마토마토마토4"));
//
//        Community community5 = communityRepository.save(new Community(user, "토마토는 맛있다5", content));
//        communitySelectedTagRespository.save(new CommunitySelectedTag(community5, communityTag));
//        for (int i = 0; i < 5; i++) {
//            communityHeartRepository.save(new CommunityHeart(community5, user));
//        }
//        communityCommentRepository.save(new CommunityComment(community5, user, "토마토마토마토마토5"));
//    }


private void createCommunitySample() {
    User user1 = userRepository.findById(1L).orElseThrow();
    User user2 = userRepository.findById(2L).orElseThrow();
    User user3 = userRepository.findById(3L).orElseThrow();
    User user4 = userRepository.findById(4L).orElseThrow();
    User user5 = userRepository.findById(5L).orElseThrow();
    User user6 = userRepository.findById(6L).orElseThrow();
    User user7 = userRepository.findById(7L).orElseThrow();
    User user8 = userRepository.findById(8L).orElseThrow();
    User user9 = userRepository.findById(9L).orElseThrow();
    User user10 = userRepository.findById(10L).orElseThrow();
    User user11 = userRepository.findById(11L).orElseThrow();
    User user12 = userRepository.findById(12L).orElseThrow();
    User user13 = userRepository.findById(13L).orElseThrow();
    CommunityTag communityTag_tomato = communityTagRepository.findById(1L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_chili = communityTagRepository.findById(2L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_corn = communityTagRepository.findById(3L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_cucumber = communityTagRepository.findById(4L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_bean = communityTagRepository.findById(5L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_eggplant = communityTagRepository.findById(6L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_daikon = communityTagRepository.findById(7L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_lettuce = communityTagRepository.findById(8L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_cabbage = communityTagRepository.findById(9L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_potato  = communityTagRepository.findById(10L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_sweat_potato = communityTagRepository.findById(11L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_large_green_onion = communityTagRepository.findById(12L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_veranda = communityTagRepository.findById(13L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_weekend_farm = communityTagRepository.findById(14L).orElseThrow(); // 이름 변경
    CommunityTag communityTag_school_farm = communityTagRepository.findById(15L).orElseThrow(); // 이름 변경

    // 선호 태그
    CommunityFavoriteTag communityFavoriteTag = new CommunityFavoriteTag();
    communityFavoriteTag.setUser(user11);
    communityFavoriteTag.setCommunityTag(communityTag_tomato); // 변경된 변수 사용
    communityFavoriteTagRepository.save(communityFavoriteTag);

    String content = "토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다";

    Community community1 = communityRepository.save(new Community(user11, "토마토는 맛있다1", content,generateRandomDateTime() ));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community1, communityTag_tomato)); // 변경된 변수 사용
    communityHeartRepository.save(new CommunityHeart(community1, user11));
    communityCommentRepository.save(new CommunityComment(community1, user11, "토마토마토마토마토1"));

    Community community2 = communityRepository.save(new Community(user11, "토마토는 맛있다2", content, generateRandomDateTime() ));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community2, communityTag_tomato)); // 변경된 변수 사용
    for (int i = 0; i < 2; i++) {
        communityHeartRepository.save(new CommunityHeart(community2, user11));
    }
    communityCommentRepository.save(new CommunityComment(community2, user11, "토마토마토마토마토2"));

    Community community3 = communityRepository.save(new Community(user11, "토마토는 맛있다3", content, generateRandomDateTime() ));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community3, communityTag_tomato)); // 변경된 변수 사용
    for (int i = 0; i < 3; i++) {
        communityHeartRepository.save(new CommunityHeart(community3, user11));
    }
    communityCommentRepository.save(new CommunityComment(community3, user11, "토마토마토마토마토3"));

    Community community4 = communityRepository.save(new Community(user11, "토마토는 맛있다4", content,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community4, communityTag_tomato)); // 변경된 변수 사용
    for (int i = 0; i < 4; i++) {
        communityHeartRepository.save(new CommunityHeart(community4, user11));
    }
    communityCommentRepository.save(new CommunityComment(community4, user11, "토마토마토마토마토4"));

    Community community5 = communityRepository.save(new Community(user11, "토마토는 맛있다5", content,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community5, communityTag_tomato)); // 변경된 변수 사용
    for (int i = 0; i < 5; i++) {
        communityHeartRepository.save(new CommunityHeart(community5, user11));
    }
    communityCommentRepository.save(new CommunityComment(community5, user11, "토마토마토마토마토5"));

    // 추가 커뮤니티
    String additionalContent = "토마토에 대한 다양한 이야기입니다.";

    Community community6 = communityRepository.save(new Community(user11, "토마토를 활용한 요리", additionalContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community6, communityTag_tomato));
    communityHeartRepository.save(new CommunityHeart(community6, user12));
    communityCommentRepository.save(new CommunityComment(community6, user11, "요리법을 공유해주세요!"));

    Community community7 = communityRepository.save(new Community(user11, "토마토 재배 팁", additionalContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community7, communityTag_tomato));
    communityHeartRepository.save(new CommunityHeart(community7, user13));
    communityCommentRepository.save(new CommunityComment(community7, user11, "재배하기 쉽나요?"));

    Community community8 = communityRepository.save(new Community(user11, "토마토의 효능", additionalContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community8, communityTag_tomato));
    communityHeartRepository.save(new CommunityHeart(community8, user11));
    communityCommentRepository.save(new CommunityComment(community8, user12, "정말 건강에 좋습니다!"));

    Community community9 = communityRepository.save(new Community(user11, "토마토와 다이어트", additionalContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community9, communityTag_tomato));
    communityHeartRepository.save(new CommunityHeart(community9, user13));
    communityCommentRepository.save(new CommunityComment(community9, user11, "다이어트에 효과적이에요!"));

    Community community10 = communityRepository.save(new Community(user11, "토마토의 다양한 품종", additionalContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community10, communityTag_tomato));
    communityHeartRepository.save(new CommunityHeart(community10, user12));
    communityCommentRepository.save(new CommunityComment(community10, user11, "어떤 품종이 좋나요?"));

    // 배추 커뮤니티
    String cabbageContent = "배추로 만드는 김치";
    Community community11 = communityRepository.save(new Community(user12, "김치 만들기", cabbageContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community11, communityTag_cabbage));
    communityHeartRepository.save(new CommunityHeart(community11, user11));
    communityCommentRepository.save(new CommunityComment(community11, user13, "김치가 최고입니다!"));

// 감자 커뮤니티
    String potatoContent = "감자 요리 레시피";
    Community community12 = communityRepository.save(new Community(user13, "감자 요리법", potatoContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community12, communityTag_potato));
    communityHeartRepository.save(new CommunityHeart(community12, user11));
    communityCommentRepository.save(new CommunityComment(community12, user12, "감자는 정말 다재다능해요!"));

// 고구마 커뮤니티
    String sweetPotatoContent = "고구마의 다양한 활용";
    Community community13 = communityRepository.save(new Community(user11, "고구마 요리법", sweetPotatoContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community13, communityTag_sweat_potato));
    communityHeartRepository.save(new CommunityHeart(community13, user12));
    communityCommentRepository.save(new CommunityComment(community13, user13, "고구마 맛있어요!"));

// 대파 커뮤니티
    String greenOnionContent = "대파의 다양한 요리법";
    Community community14 = communityRepository.save(new Community(user12, "대파 요리", greenOnionContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community14, communityTag_large_green_onion));
    communityHeartRepository.save(new CommunityHeart(community14, user11));
    communityCommentRepository.save(new CommunityComment(community14, user13, "대파는 맛을 더해줘요!"));

// 시금치 커뮤니티
    String spinachContent = "시금치의 건강 효능";
    Community community15 = communityRepository.save(new Community(user11, "시금치 효능", spinachContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community15, communityTag_lettuce)); // 상추 태그 사용
    communityHeartRepository.save(new CommunityHeart(community15, user12));
    communityCommentRepository.save(new CommunityComment(community15, user13, "시금치가 정말 좋습니다!"));

// 브로콜리 커뮤니티
    String broccoliContent = "브로콜리의 요리법";
    Community community16 = communityRepository.save(new Community(user12, "브로콜리 요리 레시피", broccoliContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community16, communityTag_cabbage)); // 배추 태그 사용
    communityHeartRepository.save(new CommunityHeart(community16, user11));
    communityCommentRepository.save(new CommunityComment(community16, user13, "브로콜리는 건강식입니다!"));

// 당근 커뮤니티
    String carrotContent = "당근의 효능과 요리법";
    Community community17 = communityRepository.save(new Community(user13, "당근 요리", carrotContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community17, communityTag_cucumber)); // 오이 태그 사용
    communityHeartRepository.save(new CommunityHeart(community17, user11));
    communityCommentRepository.save(new CommunityComment(community17, user12, "당근은 비타민이 풍부해요!"));

// 파프리카 커뮤니티
    String paprikaContent = "파프리카의 다양한 활용";
    Community community18 = communityRepository.save(new Community(user11, "파프리카 활용 팁", paprikaContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community18, communityTag_chili)); // 고추 태그 사용
    communityHeartRepository.save(new CommunityHeart(community18, user13));
    communityCommentRepository.save(new CommunityComment(community18, user12, "파프리카는 색감이 예쁩니다!"));

// 가지 커뮤니티
    String eggplantContent2 = "가지의 다양한 요리법";
    Community community19 = communityRepository.save(new Community(user12, "가지 요리 레시피", eggplantContent2,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community19, communityTag_eggplant)); // 가지 태그 사용
    communityHeartRepository.save(new CommunityHeart(community19, user11));
    communityCommentRepository.save(new CommunityComment(community19, user13, "가지 요리가 맛있어요!"));

// 고구마 커뮤니티
    String sweetPotatoContent2 = "고구마 요리 아이디어";
    Community community20 = communityRepository.save(new Community(user13, "고구마 활용법", sweetPotatoContent2,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community20, communityTag_sweat_potato)); // 고구마 태그 사용
    communityHeartRepository.save(new CommunityHeart(community20, user12));
    communityCommentRepository.save(new CommunityComment(community20, user11, "고구마는 정말 맛있습니다!"));

// 샐러리 커뮤니티
    String celeryContent = "샐러리의 효능과 사용법";
    Community community21 = communityRepository.save(new Community(user11, "샐러리 활용법", celeryContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community21, communityTag_lettuce)); // 상추 태그 사용
    communityHeartRepository.save(new CommunityHeart(community21, user12));
    communityCommentRepository.save(new CommunityComment(community21, user13, "샐러리는 다이어트에 좋습니다!"));

// 파 커뮤니티
    String chiveContent = "파의 다양한 요리법";
    Community community22 = communityRepository.save(new Community(user12, "파 요리 아이디어", chiveContent,  generateRandomDateTime()));
    communitySelectedTagRespository.save(new CommunitySelectedTag(community22, communityTag_large_green_onion)); // 대파 태그 사용
    communityHeartRepository.save(new CommunityHeart(community22, user11));
    communityCommentRepository.save(new CommunityComment(community22, user13, "파는 요리에 필수입니다!"));


    }
    private static LocalDateTime generateRandomDateTime() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        Random random = new Random();
        long randomDays = random.nextInt((int) daysBetween + 1);
        LocalDate randomDate = startDate.plusDays(randomDays);
        int randomHour = random.nextInt(24);
        int randomMinute = random.nextInt(60);
        int randomSecond = random.nextInt(60);
        return randomDate.atTime(randomHour, randomMinute, randomSecond);
    }
}
