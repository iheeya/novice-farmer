package com.d207.farmer.service.mainpage;

import com.d207.farmer.domain.community.*;
import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.FarmTodo;
import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.plant.PlantGrowthIllust;
import com.d207.farmer.domain.plant.PlantThreshold;
import com.d207.farmer.domain.user.FavoritePlace;
import com.d207.farmer.domain.user.FavoritePlant;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.mainpage.MainPageResponseDTO;
import com.d207.farmer.dto.mainpage.components.*;
import com.d207.farmer.repository.farm.*;
import com.d207.farmer.repository.mainpage.*;
import com.d207.farmer.repository.plant.PlantRepository;
import com.d207.farmer.repository.user.UserRepository;
import com.d207.farmer.utils.DateUtil;
import com.d207.farmer.utils.FarmUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageService {

    private final FarmRepository farmRepository;
    private final FavoritePlaceForFarmRepository favoritePlaceRepository;
    private final FavoritePlantForFarmRepository favoritePlantRepository;
    private final PlantRepository plantRepository;
    private final UserPlaceRepository userPlaceRepository;
    private final FarmTodoRepository todoRepository;
    private final UserRepository userRepository;
    private final CommunityFavoriteTagForMainPageRepository communityFavoriteTagRepository;
    private final CommunityTagForMainPageRepository communityTagRepository;
    private final CommunitySelectedTagForMainPageRepository communitySelectedTagRepository;
    private final CommunityHeartForMainPageRepository communityHeartRepository;
    private final CommunityCommentForMainPageRepository communityCommentRepository;
    private final CommunityImageForMainPageRepository communityImageRepository;
    private final DateUtil dateUtil;
    private final FarmUtil farmUtil;

    public MainPageResponseDTO getMainPage(Long userId) {
        // db 조회
        List<Farm> farms = farmRepository.findByUserIdWithCurrentGrowing(userId).orElse(null);
        farms = farms.isEmpty() ? null : farms;
        List<FavoritePlant> favoritePlants = favoritePlantRepository.findByUserId(userId);
        favoritePlants = favoritePlants.isEmpty() ? null : favoritePlants;
        List<FavoritePlace> favoritePlaces = favoritePlaceRepository.findByUserId(userId);
        favoritePlaces = favoritePlaces.isEmpty() ? null : favoritePlaces;
        List<UserPlace> userPlaces = userPlaceRepository.findByUserIdWithPlace(userId);
        List<Plant> plants = plantRepository.findAll();

        TodoInfoComponentDTO todoInfoComponent = getTodoInfo(userId, farms); // 중간완성
        BeginnerInfoComponentDTO beginnerInfoComponent = getBeginnerInfo(userId, farms, favoritePlants); // 완성
        MyFarmListInfoComponentDTO myFarmListInfoComponent = getMyFarmListInfo(userId, farms, userPlaces); // 완성
        FarmGuideInfoComponentDTO farmGuideInfoComponent = getFarmGuideInfo(userId); // 완성
        FavoritesInfoComponentDTO favoritesInfoComponent = getFavoritesInfo(userId, farms, favoritePlants, favoritePlaces); // 완성
        MyPlantInfoComponentDTO myPlantInfoComponent = getMyPlantInfo(userId, farms); // 완성
        RecommendInfoComponentDTO recommendInfoComponent = getRecommendInfo(userId, plants); // 완성(추천없이)
        CommunityInfoComponentDTO communityInfoComponent = getCommunityInfo(userId, favoritePlants, farms); // 완성
        WeekendFarmComponentDTO weekendFarmComponent = getWeekendFarm(userId); // 완성

        return new MainPageResponseDTO(todoInfoComponent, beginnerInfoComponent, myFarmListInfoComponent, farmGuideInfoComponent, favoritesInfoComponent,
                myPlantInfoComponent, recommendInfoComponent, communityInfoComponent, weekendFarmComponent);
    }

    /**
     * 1. TO DO 컴포넌트
     */
    private TodoInfoComponentDTO getTodoInfo(Long userId, List<Farm> farms) {
        if(farms == null) return new TodoInfoComponentDTO(false, null, null, null, null, null, null, null, null, null);

        List<Long> farmIds = farms.stream().map(Farm::getId).toList();
        List<FarmTodo> farmTodos = todoRepository.findByFarmIdInAndIsCompletedFalseOrderByTodoDate(farmIds);

        if(farmTodos == null || farmTodos.isEmpty()) {
            return new TodoInfoComponentDTO(false, null, null, null, null, null, null, null, null, null);
        }
        FarmTodo farmTodo = farmTodos.get(0);
        String title = switch (farmTodo.getTodoType()) {
            case WATERING -> "물을 줘야해요!";
            case FERTILIZERING -> "비료를 줘야해요!";
            case HARVESTING -> "수확을 해아해요!";
            case NATURE -> "기상특보!";
            case PANDEMIC -> "전염병 주의!";
        };

        // 현재 생장단계 구하기
        int growthStep = farmUtil.getGrowthStep(farmTodo.getFarm());

        String imagePath = "";
        // 일러스트 이미지 경로
        for (PlantGrowthIllust pgi : farmTodo.getFarm().getPlant().getPlantGrowthIllusts()) {
            if(pgi.getStep() == growthStep) {
                imagePath = pgi.getImagePath();
                break;
            }
        }

        // TODO 기온
        String temperature = "25도";

        return new TodoInfoComponentDTO(true, farmTodo.getTodoType(), title, farmTodo.getFarm().getUserPlace().getName(),
                farmTodo.getFarm().getMyPlantName(), imagePath, farmUtil.getGrowthStep(farmTodo.getFarm()), farmTodo.getTodoDate().toLocalDate(),
                farmTodo.getFarm().getUserPlace().getAddress().getJibun(), temperature);
    }

    /**
     * 2. 초보자용 컴포넌트
     */
    private BeginnerInfoComponentDTO getBeginnerInfo(Long userId, List<Farm> farms, List<FavoritePlant> favoritePlants) {
        // 키우는 작물이 없고 선호 작물이 없으면
//        if (farms != null || favoritePlants != null) {
        if (!userId.equals(13L) && (farms != null || favoritePlants != null)) { // FIXME 메인페이지 계정용
            return new BeginnerInfoComponentDTO(false, new ArrayList<>());
        }

        List<BeginnerInfoComponentDTO.plantInfoDTO> plantInfoDTOs = new ArrayList<>();
        plantInfoDTOs.add(new BeginnerInfoComponentDTO.plantInfoDTO(1L, "토마토", "초보 농부가 키우기 쉬운 토마토!"));
        plantInfoDTOs.add(new BeginnerInfoComponentDTO.plantInfoDTO(3L, "상추", "수확 주기가 짧아서 키우는 맛이 나는 상추!"));

        return new BeginnerInfoComponentDTO(true, plantInfoDTOs);
    }

    /**
     * 3. 내 텃밭 컴포넌트
     */
    private MyFarmListInfoComponentDTO getMyFarmListInfo(Long userId, List<Farm> farms, List<UserPlace> userPlaces) {
        if(farms == null) { // 항상 떠있어야 하는 컴포넌트이기 때문에 isUsable은 항상 true
            return new MyFarmListInfoComponentDTO(true, new ArrayList<>());
        }
        List<MyFarmListInfoComponentDTO.myFarmDTO> myFarms = new ArrayList<>();
        for (UserPlace up : userPlaces) {
            myFarms.add(new MyFarmListInfoComponentDTO.myFarmDTO(up.getPlace().getId(), up.getPlace().getName(),
                    up.getId(), up.getName()));
        }
        Collections.reverse(myFarms); // 최근에 넣은게 먼저 오게
        
        return new MyFarmListInfoComponentDTO(true, myFarms);
    }

    /**
     * 4. 텃밭 가이드 컴포넌트
     */
    private FarmGuideInfoComponentDTO getFarmGuideInfo(Long userId) {
        // 설문조사 여부와 관계없이 텃밭을 아예 만든 적이 없을 때
        List<Farm> farms = farmRepository.findByUserId(userId).orElse(null);
//        return farms == null ? new FarmGuideInfoComponentDTO(true) : new FarmGuideInfoComponentDTO(false);
        // FIXME 메인페이지 계정용
        return (farms == null || userId.equals(13L)) ? new FarmGuideInfoComponentDTO(true) : new FarmGuideInfoComponentDTO(false);
    }

    /**
     * 5. 선호 작물/장소 컴포넌트
     */
    private FavoritesInfoComponentDTO getFavoritesInfo(Long userId, List<Farm> farms, List<FavoritePlant> favoritePlants, List<FavoritePlace> favoritePlaces) {
        // 현재 키우고 있는 텃밭이 없으면
//        if(farms != null) {
        if(!(userId.equals(13L) && farms != null)) {  // FIXME 메인페이지 계정용
            return new FavoritesInfoComponentDTO(false, new ArrayList<>(), new ArrayList<>());
        }
        int componentSize = 0;
        List<FavoritesInfoComponentDTO.FavoritePlantDTO> favoritePlantDTOs = new ArrayList<>();
        if(favoritePlants != null) {
            for (FavoritePlant fp : favoritePlants) {
                if(++componentSize > 2) break;
                favoritePlantDTOs.add(new FavoritesInfoComponentDTO.FavoritePlantDTO(fp.getPlant().getId(), fp.getPlant().getName()));
            }
        }

        componentSize = 0;
        List<FavoritesInfoComponentDTO.FavoritePlaceDTO> favoritePlaceDTOs = new ArrayList<>();
        if(favoritePlaces != null) {
            for (FavoritePlace fp : favoritePlaces) {
                if(++componentSize > 2) break;
                favoritePlaceDTOs.add(new FavoritesInfoComponentDTO.FavoritePlaceDTO(fp.getPlace().getId(), fp.getPlace().getName()));
            }
        }
        return new FavoritesInfoComponentDTO(true, favoritePlantDTOs, favoritePlaceDTOs);
    }

    /**
     * 6. 내 작물 정보 컴포넌트
     */
    private MyPlantInfoComponentDTO getMyPlantInfo(Long userId, List<Farm> farms) {
        // 키우고 있는 작물이 있으면 그 작물 중 랜덤이거나 제일 관리를 많이 한
        if(farms == null) {
            return new MyPlantInfoComponentDTO(false, null, null);
        }

        // 일단 랜덤으로
        Collections.shuffle(farms, new Random());
        Farm farm = farms.get(0);
        return new MyPlantInfoComponentDTO(true, farm.getPlant().getId(), farm.getPlant().getName());
    }

    /**
     * 7. 추천 컴포넌트
     */
    private RecommendInfoComponentDTO getRecommendInfo(Long userId, List<Plant> plants) {
        // TODO 추천 알고리즘
        List<RecommendInfoComponentDTO.RecommendPlantDTO> recommendPlants = new ArrayList<>();
        String[] desc = {"라이코펜 가득!", "비타민C 풍부!", "비타민B 풍부!", "철분 가득!"};
        for (int i = 0; i < 4; i++) {
            recommendPlants.add(new RecommendInfoComponentDTO.RecommendPlantDTO(plants.get(i).getId(), plants.get(i).getName(), desc[i]));
        }
        RecommendInfoComponentDTO.RecommendByDTO recommendByPlace = new RecommendInfoComponentDTO.RecommendByDTO("서늘한 가을날, 구미시 진평동에서 키우기 좋은 작물은?",
                recommendPlants.subList(0, 2));
        RecommendInfoComponentDTO.RecommendByDTO recommendByUser = new RecommendInfoComponentDTO.RecommendByDTO("토마토를 키우는 20대 남성들에게 가장 인기있는 작물은?",
                recommendPlants.subList(2, 4));
        return new RecommendInfoComponentDTO(true, recommendByPlace, recommendByUser);
    }

    /**
     * 8. 커뮤니티 컴포넌트
     */
    private CommunityInfoComponentDTO getCommunityInfo(Long userId, List<FavoritePlant> favoritePlants, List<Farm> farms) {
        List<CommunityFavoriteTag> communityFavoriteTags = communityFavoriteTagRepository.findByUserId(userId);

        // 내가 선택해놓은 선호태그가 뜨거나
        if(communityFavoriteTags != null && !communityFavoriteTags.isEmpty()) {
            log.info("in communityFavoriteTags");
            Collections.shuffle(communityFavoriteTags, new Random());
            return getCommunityInfoComponent(userId, communityFavoriteTags.get(0).getCommunityTag());
        }

        // 선택해놓은 선호태그가 없으면 선호하는 작물/장소 태그가 뜨거나
        // TODO 장소 빠짐
        if(favoritePlants != null && !favoritePlants.isEmpty()) {
            log.info("in favoritePlants");
            String plantName = favoritePlants.get(0).getPlant().getName();
            List<CommunityTag> communityTags = communityTagRepository.findByTagName(plantName);
            return getCommunityInfoComponent(userId, communityTags.get(0));
        }

        // 그것도 없으면 키우고 있는 작물 태그가 뜨거나
        if(farms != null) {
            log.info("in farms");
            String plantName = farms.get(0).getPlant().getName();
            List<CommunityTag> communityTags = communityTagRepository.findByTagName(plantName);
            return getCommunityInfoComponent(userId, communityTags.get(0));
        }

        // 그거도 없으면 그냥 토마토
        log.info("in tomato");
        List<CommunityTag> communityTagsTomato = communityTagRepository.findByTagName("토마토");
        return getCommunityInfoComponent(userId, communityTagsTomato.get(0));
    }

    private CommunityInfoComponentDTO getCommunityInfoComponent(Long userId, CommunityTag communityTag) {
        List<CommunitySelectedTag> communitySelectedTags = communitySelectedTagRepository.findByCommunityTagId(communityTag.getId());
        List<Community> communities = new ArrayList<>();
        for (CommunitySelectedTag cst : communitySelectedTags) {
            communities.add(cst.getCommunity());
        }
        // 최신순
        List<CommunityInfoComponentDTO.CommunitySortedByRecent> communitySortedByRecents = getCommunitySortedByRecents(communities);
        // 인기순
        List<CommunityInfoComponentDTO.CommunitySortedByPopularity> communitySortedByPopularities = getCommunitySortedByPopularities(communities);

        return new CommunityInfoComponentDTO(true, communityTag.getTagName(),
                communitySortedByPopularities, communitySortedByRecents);
    }

    private List<CommunityInfoComponentDTO.CommunitySortedByPopularity> getCommunitySortedByPopularities(List<Community> communities) {
        List<Long> communityIdAlls = new ArrayList<>();
        for (Community community : communities) {
            communityIdAlls.add(community.getId());
        }
        // 좋아요 불러오기
        List<CommunityHeart> communityHearts = communityHeartRepository.findByCommunityIdIn(communityIdAlls);

        // 좋아요 맵
        Map<Long, Long> heartCntMap = new HashMap<>();
        for (CommunityHeart ch : communityHearts) {
            heartCntMap.put(ch.getCommunity().getId(), heartCntMap.getOrDefault(ch.getCommunity().getId(), 0L) + 1);
        }

        // 좋아요 개수 순으로 정렬
        List<CommunityHeartCount> heartCounts = new ArrayList<>();
        for (Long cid : heartCntMap.keySet()) {
            heartCounts.add(new CommunityHeartCount(cid, heartCntMap.get(cid)));
        }
        heartCounts.sort(Comparator.comparing(CommunityHeartCount::getHeartCount).reversed());

        int communityIdCnt = 0;
        List<Long> communityIds = new ArrayList<>();
        for (CommunityHeartCount chc : heartCounts) {
            if(++communityIdCnt > 5) break;
            communityIds.add(chc.getCommunityId());
        }

        // 이미지, 댓글 불러오기
        List<CommunityImage> communityImages = communityImageRepository.findByCommunityIdIn(communityIds);
        List<CommunityComment> communityComments = communityCommentRepository.findByCommunityIdIn(communityIds);

        // 이미지 맵(한 게시글 당 이미지 한 개만 불러와서 저장)
        Map<Long, String> imageMap = new HashMap<>();
        for (CommunityImage ci : communityImages) {
            if(!imageMap.containsKey(ci.getCommunity().getId())) {
                imageMap.put(ci.getCommunity().getId(), ci.getImagePath());
            }
        }

        // 댓글 맵(한 게시글 당 좋아요 몇 개인지 카운트)
        Map<Long, Long> commentCntMap = new HashMap<>();
        for (CommunityComment cc : communityComments) {
            commentCntMap.put(cc.getCommunity().getId(), commentCntMap.getOrDefault(cc.getCommunity().getId(), 0L) + 1);
        }

        List<CommunityInfoComponentDTO.CommunitySortedByPopularity> communitySortedByPopularities = new ArrayList<>();
        // FIXME List 조회 최적화
        for (Long ci : communityIds) {
            for (Community c : communities) {
                if(!ci.equals(c.getId())) continue;
                communitySortedByPopularities.add(new CommunityInfoComponentDTO.CommunitySortedByPopularity(
                        c.getId(), c.getTitle(),
                        (c.getContent().length() > 20 ? c.getContent().substring(0, 20) : c.getContent()) + "...",
                        imageMap.get(c.getId()), heartCntMap.get(c.getId()), commentCntMap.get(c.getId()),
                        c.getUser().getNickname(), c.getUser().getImagePath(), c.getWriteDate().toLocalDate()
                ));
                break;
            }
        }
        return communitySortedByPopularities;
    }

    private List<CommunityInfoComponentDTO.CommunitySortedByRecent> getCommunitySortedByRecents(List<Community> communities) {
        int communityIdsCnt = 0;
        List<Long> communityIds = new ArrayList<>();
        for (Community community : communities) {
            if(++communityIdsCnt > 5) break;
            communityIds.add(community.getId());
        }
        // 이미지, 좋아요, 댓글 불러오기
        List<CommunityImage> communityImages = communityImageRepository.findByCommunityIdIn(communityIds);
        List<CommunityHeart> communityHearts = communityHeartRepository.findByCommunityIdIn(communityIds);
        List<CommunityComment> communityComments = communityCommentRepository.findByCommunityIdIn(communityIds);

        // 이미지 맵(한 게시글 당 이미지 한 개만 불러와서 저장)
        Map<Long, String> imageMap = new HashMap<>();
        for (CommunityImage ci : communityImages) {
            if(!imageMap.containsKey(ci.getCommunity().getId())) {
                imageMap.put(ci.getCommunity().getId(), ci.getImagePath());
            }
        }

        // 좋아요 맵(한 게시글 당 좋아요 몇 개인지 카운트)
        Map<Long, Long> heartCntMap = new HashMap<>();
        for (CommunityHeart ch : communityHearts) {
            heartCntMap.put(ch.getCommunity().getId(), heartCntMap.getOrDefault(ch.getCommunity().getId(), 0L) + 1);
        }

        // 댓글 맵(한 게시글 당 좋아요 몇 개인지 카운트)
        Map<Long, Long> commentCntMap = new HashMap<>();
        for (CommunityComment cc : communityComments) {
            commentCntMap.put(cc.getCommunity().getId(), commentCntMap.getOrDefault(cc.getCommunity().getId(), 0L) + 1);
        }

        int communityRecentCnt = 0;
        List<CommunityInfoComponentDTO.CommunitySortedByRecent> communitySortedByRecents = new ArrayList<>();
        for (Community c : communities) {
            if(++communityRecentCnt > 5) break;
            communitySortedByRecents.add(new CommunityInfoComponentDTO.CommunitySortedByRecent(
                    c.getId(), c.getTitle(),
                    (c.getContent().length() > 20 ? c.getContent().substring(0, 20) : c.getContent()) + "...",
                    imageMap.get(c.getId()), heartCntMap.get(c.getId()), commentCntMap.get(c.getId()),
                    c.getUser().getNickname(), c.getUser().getImagePath(), c.getWriteDate().toLocalDate()
            ));
        }
        return communitySortedByRecents;
    }


    /**
     * 9. 주말농장 컴포넌트
     */
    private WeekendFarmComponentDTO getWeekendFarm(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return new WeekendFarmComponentDTO(true, user.getAddress());
    }


    @Getter
    @AllArgsConstructor
    private static class CommunityHeartCount {
        Long communityId;
        Long heartCount;
    }
}
