package com.d207.farmer.utils;

import com.d207.farmer.domain.common.Address;
import com.d207.farmer.domain.community.*;
import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.FarmTodo;
import com.d207.farmer.domain.farm.TodoType;
import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.plant.PlantGrowthIllust;
import com.d207.farmer.domain.plant.PlantThreshold;
import com.d207.farmer.domain.user.FavoritePlace;
import com.d207.farmer.domain.user.FavoritePlant;
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
import com.d207.farmer.repository.place.PlaceRepository;
import com.d207.farmer.repository.plant.PlantIllustRepository;
import com.d207.farmer.repository.plant.PlantRepository;
import com.d207.farmer.repository.plant.PlantThresholdRepository;
import com.d207.farmer.repository.user.FavoritePlaceRepository;
import com.d207.farmer.repository.user.FavoritePlantRepository;
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
import java.util.*;

import static java.lang.Boolean.TRUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupApplicationListener {

    private static final int USER_NUM = 10; // 회원 개수

    // 회원 닉네임
    private final String[] userNicknames = {"불꽃토마토", "농슐랭3스타", "농사하는돌아이", "농부카세", "농부여신", "농사대가", "주말엔농부", "당근조아", "토마토조아", "청양고추조아"};

    // 작물
    private final Object[][] plantSamples = {{"토마토", 1940, true}, {"고추", 1746, true}, {"옥수수", 2134, false}, {"오이", 742, false}, {"콩", 1060, false},
                                             {"가지", 1378, true}, {"무", 1360, true}, {"상추", 776, false}, {"배추", 1940, false}, {"감자", 1060, false},
                                             {"고구마", 1500, true}, {"대파", 2910, true}};

    // 작물 threshold
    private final int[][] plantThresholds = {
            {510, 1060, 1940}, // 토마토
            {255, 848, 1746}, // 고추
            {204, 742, 2134}, // 옥수수
            {178, 602, 742}, // 오이
            {204, 742, 1060}, // 콩
            {255, 954, 1378}, // 가지
            {680, 0, 1360}, // 무
            {388, 0, 776}, // 상추
            {970, 0, 1940}, // 배추
            {530, 0, 1060}, // 감자
            {750, 0, 1500}, // 고구마
            {1455, 0, 2910} // 대파
    };

    // 장소
    private final Object[][] placeSamples = {{"베란다", "베란다는 베란다입니다", true}, {"주말농장", "주말농장은 주말농장입니다.", true}, {"개인텃밭", "개인텃밭은 개인텃밭입니다.", false}, {"스쿨팜", "스쿨팜은 스쿨팜입니다.", false}};

    // 주말농장
    private final String[][] weekendFarmSamples = {
            {"쭌이네, BINIL HAUS", "경북 구미시 산동읍 성수4길 65 쭌이네비닐하우스", "0507-1317-4536", "36.1573359", "128.4209952", "https://image.com/fdf", "성수리 현대정비에서 산길따라~~쭈우쭈욱 올라오세요^^ (현대정비를 왼쪽에두고 굴다리지남^^)"},
            {"베리마토 딸기농장", "경북 구미시 지산양호9길 10", "010-2600-5603", "36.1358311", "128.3632623", "https://blog.naver.com/seog1017", "최원석, 배성연 부부가  땀으로 정직하게 농사짓고 있는  딸기, 토마토 농장 베리마토 입니다.   판매상품 당일 수확한 딸기 딸기 체험 주말 가족체험"},
            {"소편한농장", "경북 구미시 고아읍 외예리 406-1", "0507-1353-3070", "36.1837223", "128.2764442", "", ""},
            {"농부의정원", "경북 구미시 무을면 수다사길 96", "0507-1348-8721", "36.2762526", "128.1654738", "http://instagram.com/farmer_garden_", "키즈 체험 농장입니다 농자니아 체험 운영 중입니다"},
    };

    // 커뮤니티 태그
    private final String[] tagNames = {"토마토", "고추", "옥수수", "오이", "콩", "가지", "무", "상추", "배추", "감자", "고구마", "대파", "베란다", "주말농장", "개인텃밭", "스쿨팜",
            "쌈추", "치커리", "허브", "방울토마토", "대추", "곶감", "무농약", "유기농", "친환경", "해충방제", "수확시기", "물주기", "씨앗파종", "텃밭초보", "분갈이",
            "비료추천", "흙선택", "텃밭정리", "주말작업", "가족농사", "도시텃밭", "초보자팁", "성장일지", "병해충예방", "화분관리", "재배도구", "농업기술"};

    // 커뮤니티
    private final String[][] communityTitleAndContent = {
            {"베란다에서 상추 키우기", "베란다에서 상추를 키우고 있어요. 햇빛이 부족할 땐 어떻게 해야 할까요? 조언 부탁드립니다!"},
            {"도시텃밭에 필요한 도구", "도시텃밭을 시작하려고 합니다. 필수로 필요한 도구가 무엇인지 추천해주세요."},
            {"고구마 수확 시기 궁금해요", "처음 심어 본 고구마, 언제 수확하면 좋을까요? 색이 많이 변했는데 괜찮을까요?"},
            {"상추가 자라지 않아요", "상추를 키우고 있는데 성장이 느려요. 햇빛과 물은 충분한데, 원인이 뭘까요?"},
            {"해충 방제법 추천해요", "요즘 벌레 때문에 작물이 피해를 입고 있어요. 친환경적인 방제법 추천해주실 분 계신가요?"},
            {"옥상 텃밭, 가능한가요?", "옥상에서 텃밭을 만들어 보려고 하는데 작물 선정에 고민이 많아요. 추천 작물 있을까요?"},
            {"김장용 배추 키우기", "김장을 위해 배추를 키우고 있습니다. 병해충 예방을 어떻게 해야 할까요?"},
            {"주말농장에서의 첫 수확", "드디어 주말농장에서 처음으로 감자를 수확했습니다! 감자 보관법을 알고 싶어요."},
            {"텃밭 비료 추천 부탁드려요", "어떤 비료가 좋을지 고민 중입니다. 작물의 영양을 골고루 줄 수 있는 비료 추천해주세요."},
            {"겨울철 허브 관리 팁", "겨울에 허브가 잘 자라지 않아요. 실내에서 키우는 관리법이 궁금해요!"},
            {"주말농장 첫 방문기", "오늘 드디어 주말농장을 처음 방문했어요. 작은 밭에 심어진 작물들이 너무 예쁘네요! 벌써 다음 주말이 기대됩니다."},
            {"베란다 텃밭에서 자라는 허브", "요즘 베란다에 키우는 허브들이 잘 자라네요. 특히 바질 향이 너무 좋아서 요리에 자주 사용하게 돼요."},
            {"처음 심어본 토마토의 변화", "지난주에 심었던 토마토가 벌써 조금 자랐어요. 잎이 커지고 줄기도 더 단단해졌네요. 물 주는 재미가 있어요!"},
            {"텃밭에서의 아침 풍경", "아침 일찍 텃밭에 나가보니 상추가 싱그럽게 자라있었어요. 이른 아침 공기가 정말 상쾌하네요."},
            {"옥수수 잎에 이슬 맺힌 모습", "새벽에 본 옥수수 잎에 맺힌 이슬이 너무 예뻐서 사진으로 남겼어요. 농사짓는 즐거움이 이런 거겠죠?"},
            {"아이와 함께한 수확의 즐거움", "오늘은 아이와 함께 상추를 수확했어요. 작은 손으로 상추를 하나하나 따는 모습이 정말 사랑스럽네요."},
            {"가을이 오기 전에 감자 캐기", "가을이 오기 전에 감자를 모두 수확했습니다. 작은 감자들이 많지만 뿌듯한 하루였어요!"},
            {"베란다 텃밭의 향기로운 밤", "오늘 밤 베란다에 나갔더니 허브 향이 가득하네요. 바질과 로즈마리 덕분에 집이 향기로워요."},
            {"겨울 준비, 텃밭 정리 중", "날씨가 추워지기 전에 텃밭을 정리했어요. 내년 봄을 위해 밭을 다듬는 작업을 시작했습니다."},
            {"주말농장에서 느끼는 작은 행복", "주말마다 텃밭에 가서 작물들을 돌보는 시간이 점점 소중해져요. 자연 속에서 힐링되는 기분입니다."},
            {"상추 물주기 요령", "상추는 수분을 좋아하지만 과습은 피해야 해요. 흙이 마르면 물을 주고, 잎이 처지기 전에 물을 충분히 줍니다."},
            {"토마토 열매 잘 키우는 법", "토마토는 햇빛을 충분히 받아야 열매가 잘 자라요. 물은 과하지 않게 주고 지지대를 세워주는 게 좋습니다."},
            {"고추의 해충 방지법", "고추는 진딧물이 잘 생기는데, 친환경 비누 스프레이를 이용해 해충을 관리해 주세요."},
            {"옥수수 건강하게 키우기", "옥수수는 심은 후에 일정 간격으로 거름을 주어야 해요. 물을 깊게 주어 뿌리가 잘 내리도록 합니다."},
            {"배추 심는 시기와 방법", "배추는 여름 후반이나 가을 초에 심으면 좋아요. 심은 후 물을 충분히 주고 햇빛을 많이 받게 해주세요."},
            {"허브 가지치기 팁", "허브는 자주 가지치기를 해주면 더욱 풍성해져요. 윗부분을 잘라내면 옆으로 더 잘 자랍니다."},
            {"감자 재배 시 흙 관리", "감자는 흙이 고르게 촉촉해야 잘 자라요. 싹이 올라올 때쯤 흙을 덮어 주면 더욱 크고 건강한 감자가 됩니다."},
            {"대파 키우기 초보자 가이드", "대파는 자주 자라는 작물이에요. 자주 물을 주되, 물빠짐이 좋은 흙을 사용하는 것이 중요합니다."},
            {"가지의 병해 예방 방법", "가지는 습한 환경에서 병이 잘 생기므로 물을 줄 때는 뿌리 부분만 주고 통풍이 잘 되게 해주세요."},
            {"무 농사 필수 비법", "무는 뿌리 작물이므로 흙 속에 충분한 영양분이 필요해요. 비료를 잘 섞어 주고 햇빛을 충분히 받게 하세요."},
            {"베란다 텃밭 준비 완료!", "이번 주말에 드디어 베란다 텃밭을 완성했습니다. 상추와 허브를 심었는데 잘 자랄지 기대되네요!"},
            {"고추 심을 때 간격은?", "고추 모종을 심으려고 하는데 간격은 얼마나 두는 게 좋을까요? 주변에 심을 작물도 추천 부탁드려요."},
            {"옥수수 키우기 꿀팁", "옥수수는 옆에 다른 옥수수를 두면 수분이 잘 돼요. 배수가 잘 되는 흙을 사용하는 것도 중요합니다."},
            {"텃밭에서 본 첫 싹!", "오늘 아침에 나가보니 첫 싹이 났어요! 상추가 제일 먼저 얼굴을 내밀었네요. 기분 좋은 하루입니다."},
            {"상추가 잘 자라지 않아요", "베란다에서 상추를 키우는데, 잎이 자라다 멈추네요. 햇빛은 충분히 주고 있는데 뭐가 문제일까요?"},
            {"가을무 수확 시기", "가을무를 처음 키워보는데 수확은 언제쯤 하는 게 좋을까요? 껍질이 단단해 보이면 되는 걸까요?"},
            {"아이와 함께한 주말농장", "이번 주말 아이와 함께 주말농장에서 고구마를 수확했어요. 아이가 정말 좋아했답니다!"},
            {"초보 텃밭러의 첫 수확", "드디어 상추와 파슬리 첫 수확을 했어요! 오늘 저녁 샐러드에 쓰려고 합니다. 너무 뿌듯하네요."},
            {"친환경 해충 퇴치법", "벌레가 자꾸 생기는데, 친환경 해충 퇴치법으로 뭐가 좋을까요? 집에서 간단히 할 수 있는 방법 찾고 있어요."},
            {"로즈마리 가지치기 시기", "로즈마리를 키우고 있는데 언제 가지치기를 해야 하는지 잘 모르겠어요. 조언 부탁드려요!"},
            {"주말농장에서의 힐링 타임", "주말농장에서 소일거리하며 자연을 느끼는 시간이 요즘 가장 큰 행복이 되었어요."},
            {"배추에 병이 생겼어요", "배추 잎에 누렇게 변하는 부분이 있는데 병이 생긴 걸까요? 혹시 어떻게 해야 할지 아시는 분 계신가요?"},
            {"봄 상추 파종 시기", "봄에 상추를 언제 심는 게 좋은지 궁금해요. 너무 이른 시기에 심어도 괜찮을까요?"},
            {"옥상 텃밭에서의 하루", "옥상 텃밭에서 하루 종일 보내다 보니 몸은 고단해도 마음은 가벼워지네요. 이것이 농사의 매력인가 봅니다."},
            {"허브가 무성하게 자라려면?", "허브는 가지치기를 자주 해줘야 무성해져요. 상단부를 잘라주면 옆으로 더 많이 퍼지더라고요."},
            {"고구마 심는 방법 궁금해요", "고구마 심는 방법과 준비해야 할 것들이 뭐가 있을까요? 텃밭 초보라 잘 모르겠어요."},
            {"옥수수 잎이 말라가요", "옥수수를 키우고 있는데 잎이 갈색으로 변해요. 물은 충분히 주고 있는데 왜 그럴까요?"},
            {"감자 보관 방법", "감자를 수확했는데 어떻게 보관하는 게 좋을까요? 바로 먹지 않을 때 어떻게 보관해야 오래 갈까요?"},
            {"주말농장 친구들과 나눈 재배 노하우", "오늘 주말농장 친구들과 서로의 재배 노하우를 공유했어요. 많은 팁을 얻어가서 뿌듯하네요!"},
            {"무 농사 준비 중!", "올해는 무를 키우기로 했어요. 처음이라 잘할 수 있을지 걱정이 되지만 재미있게 해보려고 합니다!"}
    };

    // 커뮤니티 댓글
    private final String[][] communityComments = {
            {"햇빛이 부족할 땐 햇빛이 잘 드는 곳으로!", "상추 맛있겠다"},
            {"쿠팡에서 재료 주문하세요", "호미 있으면 인생이 달라짐"},
            {"고구마는 보통 잎이 누렇게 변할 때쯤 수확해요!", "제 고구마는 더 일찍 수확했어요~"},
            {"햇빛이 약하면 LED 식물등을 고려해보세요.", "상추가 잘 자라길 바랍니다!"},
            {"친환경 스프레이 추천해요!", "벌레가 줄어들었으면 좋겠어요"},
            {"옥상 텃밭에는 허브도 좋아요!", "작물 심기 전에 바닥 배수 꼭 신경 쓰세요."},
            {"배추는 병이 생기기 쉬우니 자주 확인하세요.", "김장 준비 파이팅입니다!"},
            {"감자는 시원하고 어두운 곳에 보관하세요.", "수확 축하드려요! 감자전 추천해요."},
            {"유기농 비료도 좋아요! 자주 주지 말고 간격을 두세요.", "텃밭 화이팅입니다!"},
            {"허브는 실내에서 키울 때 가습기 틀어주면 좋아요.", "겨울에도 무럭무럭 자라길 바랍니다!"},
            {"주말농장이 생생하게 그려지네요! 다음 주말도 기대됩니다!"},
            {"허브 향 너무 좋아요! 요리에 쓸 때마다 뿌듯하시겠어요.", "바질이 진짜 잘 자라네요! 허브 키우기 정말 즐겁죠!"},
            {"토마토가 잘 자라서 다행이네요! 열매 맺힐 날이 기대됩니다.", "물 줄 때마다 쑥쑥 자라네요. 곧 빨갛게 익을 거예요!"},
            {"아침 텃밭 풍경이라니 너무 평화로워 보여요.", "상추가 싱그럽게 자라다니! 건강한 텃밭이네요."},
            {"옥수수 이슬 맺힌 모습 상상돼요! 사진 찍으셨다니 부럽습니다.", "이슬 맺힌 모습이 예쁠 것 같아요. 멋진 사진 기대할게요!"},
            {"아이와 함께 수확이라니 정말 소중한 시간이었겠어요!", "작은 손으로 따는 모습이라니 너무 귀여워요!", "상추 수확 축하드려요! 맛있게 드세요~"},
            {"감자 수확 수고 많으셨어요! 감자 요리 추천드려요.", "가을 감자라니 정말 맛있을 것 같아요!", "작은 감자들도 알차게 수확하셨네요!"},
            {"허브 향 가득한 밤이라니 상상만으로도 힐링이네요.", "바질이랑 로즈마리 덕분에 집이 향기로우시겠어요!", "베란다가 허브 정원 같겠어요!"},
            {"겨울 준비 잘 하셨네요! 내년 봄이 벌써 기대돼요.", "텃밭을 정리하니 마음도 깔끔해지셨겠어요.", "봄을 준비하는 마음, 정말 멋집니다!"},
            {"주말농장에서 마음의 힐링, 정말 좋네요!", "작물 키우는 시간이 소중해진다니 공감됩니다!", "자연 속에서 힐링이 정말 필요해요!", "다음 주말도 텃밭에서 행복한 시간 보내세요!"},
            {"상추 물주기 팁 감사합니다! 과습이 문제군요.", "상추가 잘 자랄 수 있도록 물 잘 주겠습니다!"},
            {"토마토에 햇빛이 중요하다니, 더 잘 키우기 위해 햇빛 잘 받는 곳으로 옮길게요!", "지지대 세우는 거 잊지 말아야겠네요."},
            {"친환경 비누 스프레이 사용해보겠습니다! 진딧물 때문에 고민했거든요.", "고추가 무사히 자라길 바랍니다!"},
            {"거름 주는 타이밍이 중요하군요! 감사해요.", "물 깊게 주는 것도 신경 써야겠어요!"},
            {"배추 심는 시기 이제 알았네요. 여름 후반에 심을게요!", "햇빛을 잘 받게 하는 거, 정말 중요해요!"},
            {"허브 가지치기 자주 해주면 더 잘 자라네요! 잊지 않고 해보겠습니다.", "풍성한 허브를 위해 꾸준히 가지치기할게요!"},
            {"감자 재배에 대해 유용한 정보 감사합니다!", "흙을 고르게 촉촉하게 유지해야겠어요."},
            {"대파가 자주 자란다니 잘 키울 수 있을 것 같아요!", "물빠짐 좋은 흙 사용해야겠네요. 감사합니다!"},
            {"가지는 통풍이 중요하군요! 더욱 주의해야겠어요.", "뿌리 부분만 물주면 더 좋겠네요!"},
            {"무 재배에 필요한 영양분 잘 챙길게요!", "햇빛도 충분히 받게 해야겠군요. 정보 감사합니다!"},
            {"베란다에서 상추와 허브 키우는 게 너무 좋겠어요!", "저도 텃밭 만들고 싶어요. 잘 자라길 바랍니다!"},
            {"고추 간격에 대해 좋은 팁 감사해요! 고민이 많았거든요.", "주변 작물도 잘 자라도록 조언해 주시면 감사하겠습니다!"},
            {"옥수수는 배수 잘 되는 흙이 중요하다니, 다음에 참고하겠습니다!", "수분 잘 유지할 수 있도록 할게요!"},
            {"와, 첫 싹이 났다니 축하해요! 상추가 잘 자라길 바랍니다!"},
            {"상추가 잘 자라지 않는다면, 흙 상태도 체크해보세요. 영양분이 부족할 수 있어요."},
            {"가을무 수확 시기, 보통 뿌리가 두께가 5~6cm 정도 될 때 수확하면 좋아요."},
            {"아이와 함께한 수확이라니! 정말 좋은 추억이네요. 아이가 좋아하는 모습이 상상돼요."},
            {"첫 수확 축하드립니다! 샐러드 맛있겠어요. 앞으로 더 많은 수확이 있길 바래요."},
            {"친환경 방법으로는 물과 식초를 섞어 뿌리면 효과적이에요. 한번 시도해보세요!"},
            {"로즈마리는 성장기에 가지치기 해주면 더욱 풍성해져요. 봄에 해보세요."},
            {"자연 속에서의 시간, 정말 소중하죠! 저도 힐링되는 기분이에요."},
            {"배추에 병이 생긴 것 같다면, 빠르게 치료해주는 것이 중요해요. 방제를 고려해보세요."},
            {"봄에 상추는 3월 초에 심는 것이 좋아요. 너무 이르지 않게 심어보세요."},
            {"옥상에서의 농사, 정말 매력적이죠! 고단해도 보람이 있겠어요."},
            {"허브가 무성하게 자라려면 자주 가지치기해주면 좋아요. 잘 해보세요!"},
            {"고구마 심는 법은 깊이 10cm 정도로 심고, 충분한 간격을 두는 것이 중요해요."},
            {"옥수수 잎이 마르는 건 수분 부족이나 영양 부족일 수 있어요. 체크해보세요!"},
            {"감자는 통풍이 잘되는 곳에 보관해야 해요. 신문지에 싸서 보관하는 것도 좋아요."},
            {"서로의 노하우를 공유하는 건 정말 좋은 경험이네요! 계속해서 배우세요."},
            {"무 농사는 처음이라면 조금씩 배워가며 하세요! 재미있게 해보세요!"}
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
    private final FavoritePlaceRepository favoritePlaceRepository;
    private final FavoritePlantRepository favoritePlantRepository;
    private final PlaceRepository placeRepository;
    private final DateUtil dateUtil;
    private final CommunityImageRepository communityImageRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Application ready");
        createPlantSample();
        createPlaceSample();
//        createWeekendFarmSample(); // import.sql로 들어감
        createPlantIllustSample();
        createPlantThresholdSample();
        createUserSample();
        createFarmSample();
        createTodoSample();
        createCommunityTagSample();
        createCommunitySample();
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

//    private void createWeekendFarmSample() {
//        for (int i = 0; i < weekendFarmSamples.length; i++) {
//            weekendFarmService.registerWeekendFarm(new WeekendFarmRegisterRequestDTO(weekendFarmSamples[i][0], weekendFarmSamples[i][1], weekendFarmSamples[i][2],
//                    weekendFarmSamples[i][3], weekendFarmSamples[i][4], weekendFarmSamples[i][5], weekendFarmSamples[i][6]));
//        }
//    }

    private void createPlantIllustSample() {
        List<Plant> plants = plantRepository.findAll();
        boolean isThirdStep = false;
        for (Plant plant : plants) {
            int n = isThirdStep ? 4 : 5;
            for (int i = 1; i < n; i++) {
                plantIllustRepository.save(new PlantGrowthIllust(plant, i, "plant/" + plant.getName() + i + ".png"));
            }
            if(plant.getName().equals("가지")) isThirdStep = true;
        }
    }

    private void createPlantThresholdSample() {
        List<Plant> plants = plantRepository.findAll();
        boolean isThirdStep = false;
        for (Plant plant : plants) {
            int id = plant.getId().intValue();
            if(isThirdStep) {
                plantThresholdRepository.save(new PlantThreshold(plant, 1, plantThresholds[id-1][0]));
                plantThresholdRepository.save(new PlantThreshold(plant, 2, plantThresholds[id-1][2]));
            } else {
                plantThresholdRepository.save(new PlantThreshold(plant, 1, plantThresholds[id-1][0]));
                plantThresholdRepository.save(new PlantThreshold(plant, 2, plantThresholds[id-1][1]));
                plantThresholdRepository.save(new PlantThreshold(plant, 3, plantThresholds[id-1][2]));
            }
            if(plant.getName().equals("가지")) isThirdStep = true;
        }
    }

    private void createUserSample() {
        // test 계정 - 가입
        for (int i = 1; i < USER_NUM + 1; i++) {
            UserSampleRegisterRequestDTO u = new UserSampleRegisterRequestDTO(
                    "test" + i + "@email.com", "1234", userNicknames[i-1], 20 + i, Gender.MALE,
                    "경상북도 구미시", true, userNicknames[i-1] + ".png"
            );
            User user = sampleService.registerUser(u);
        }

        // farm1@email.com - 가입(11L)
        UserSampleRegisterRequestDTO u1 = new UserSampleRegisterRequestDTO( // id 11번
                "farm1@email.com", "1234", "내손이호미", 25, Gender.MALE,
                "경상북도 구미시", true, "farmer1.png"
        );
        User farm1User = sampleService.registerUser(u1);

        // farm1@email.com - 선호 작물/장소
        List<Long> plantIds = new ArrayList<>();
        plantIds.add(1L); plantIds.add(2L);
        List<Plant> plants = plantRepository.findByIdIn(plantIds);
        favoritePlantRepository.save(new FavoritePlant(farm1User, plants.get(0)));
        favoritePlantRepository.save(new FavoritePlant(farm1User, plants.get(1)));

        List<Long> placeIds = new ArrayList<>();
        placeIds.add(1L); placeIds.add(2L);
        List<Place> places = placeRepository.findByIdIn(placeIds);
        favoritePlaceRepository.save(new FavoritePlace(farm1User, places.get(0)));
        favoritePlaceRepository.save(new FavoritePlace(farm1User, places.get(1)));


        // farm2@email.com - 가입(12L)
        UserSampleRegisterRequestDTO u2 = new UserSampleRegisterRequestDTO(
                "farm2@email.com", "1234", "귀농각", 25, Gender.MALE,
                "경상북도 구미시", true, "farmer2.png"
        );
        User farm2User = sampleService.registerUser(u2);

        // farm2@email.com - 선호 작물/장소
        favoritePlantRepository.save(new FavoritePlant(farm2User, plants.get(0)));
        favoritePlaceRepository.save(new FavoritePlace(farm2User, places.get(0)));


        // mainpage@email.com - 가입(13L)
        UserSampleRegisterRequestDTO u3 = new UserSampleRegisterRequestDTO(
                "mainpage@email.com", "1234", "농부의정원", 30, Gender.MALE,
                "대구광역시 달성군", true, "mainpage.png"
        );
        User mainpageUser = sampleService.registerUser(u3);

        // mainpage@email.com - 선호 작물/장소
        favoritePlantRepository.save(new FavoritePlant(mainpageUser, plants.get(0)));
        favoritePlantRepository.save(new FavoritePlant(mainpageUser, plants.get(1)));
        favoritePlaceRepository.save(new FavoritePlace(mainpageUser, places.get(0)));
        favoritePlaceRepository.save(new FavoritePlace(mainpageUser, places.get(1)));
    }

    private void createFarmSample() {
        Address address = new Address("경북", "구미시", "", "임수동", null, "경북 구미시 임수동 94-1", "39388");

        FarmPlaceRegisterDTO farmPlace1 = new FarmPlaceRegisterDTO(1L, address);
        FarmPlantRegisterDTO farmPlant1 = new FarmPlantRegisterDTO(1L, "토순이", "토마토 냠냠");
        FarmRegisterRequestDTO farmRegister1 = new FarmRegisterRequestDTO(farmPlace1, farmPlant1);
        Farm farm1 = farmService.registerFarm(11L, farmRegister1); // 1L
        farm1.startGrow(LocalDateTime.now().minusDays(50));

        Farm farm = farmRepository.findById(1L).orElseThrow();
        farm.updateDegreeDay(1000);

        FarmPlaceRegisterDTO farmPlace2 = new FarmPlaceRegisterDTO(2L, address);
        FarmPlantRegisterDTO farmPlant2 = new FarmPlantRegisterDTO(2L, "작매고", "작은 고추가 매움");
        FarmRegisterRequestDTO farmRegister2 = new FarmRegisterRequestDTO(farmPlace2, farmPlant2);
        Farm farm2 = farmService.registerFarm(11L, farmRegister2); // 2L
        farm2.startGrow(LocalDateTime.now().minusDays(20));

        myPlantService.harvestPlant(11L, new ManagePlantRequestDTO(2L));
        myPlantService.endPlant(11L, new ManagePlantRequestDTO(2L));

        FarmPlaceRegisterDTO farmPlace3 = new FarmPlaceRegisterDTO(3L, address);
        FarmPlantRegisterDTO farmPlant3 = new FarmPlantRegisterDTO(8L, "상충", "쌈쌈");
        FarmRegisterRequestDTO farmRegister3 = new FarmRegisterRequestDTO(farmPlace3, farmPlant3);
        Farm farm3 = farmService.registerFarm(11L, farmRegister3); // 3L
        farm3.startGrow(LocalDateTime.now().minusDays(20));

        // mainpage 계정용
        Farm farm4 = farmService.registerFarm(13L, farmRegister1); // 4L
        Farm farm5 = farmService.registerFarm(13L, farmRegister2); // 5L

        farm4.startGrow(LocalDateTime.now().minusDays(50));
        farm5.startGrow(LocalDateTime.now().minusDays(20));

        farmRepository.findById(4L).orElseThrow().updateDegreeDay(1000);
        farmRepository.findById(5L).orElseThrow().updateDegreeDay(1000);
    }

    private void createTodoSample() {
        Farm farm = farmRepository.findById(1L).orElseThrow();
        farmTodoRepository.save(new FarmTodo(farm, TodoType.WATERING, "", false, LocalDateTime.now().plusDays(1), null));
        farmTodoRepository.save(new FarmTodo(farm, TodoType.FERTILIZERING, "", false, LocalDateTime.now().plusDays(6), null));
        farmTodoRepository.save(new FarmTodo(farm, TodoType.WATERING, "", true, LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(2)));
        farmTodoRepository.save(new FarmTodo(farm, TodoType.FERTILIZERING,"", true, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5)));

        Farm farm3 = farmRepository.findById(3L).orElseThrow();
        farmTodoRepository.save(new FarmTodo(farm3, TodoType.WATERING, "", false, LocalDateTime.now().plusDays(1), null));
        farmTodoRepository.save(new FarmTodo(farm3, TodoType.FERTILIZERING, "", false, LocalDateTime.now().plusDays(6), null));
        farmTodoRepository.save(new FarmTodo(farm3, TodoType.WATERING, "", true, LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(2)));
        farmTodoRepository.save(new FarmTodo(farm3, TodoType.FERTILIZERING,"", true, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5)));

        // mainpage 용
        Farm farmMainPage1 = farmRepository.findById(4L).orElseThrow();
        farmTodoRepository.save(new FarmTodo(farmMainPage1, TodoType.WATERING, "", false, LocalDateTime.now().plusDays(1), null));
        farmTodoRepository.save(new FarmTodo(farmMainPage1, TodoType.FERTILIZERING, "", false, LocalDateTime.now().plusDays(6), null));
        farmTodoRepository.save(new FarmTodo(farmMainPage1, TodoType.NATURE, "폭우주의보", false, LocalDateTime.now(), null));

        Farm farmMainPage2 = farmRepository.findById(5L).orElseThrow();
        farmTodoRepository.save(new FarmTodo(farmMainPage2, TodoType.WATERING, "", false, LocalDateTime.now().plusDays(1), null));
        farmTodoRepository.save(new FarmTodo(farmMainPage2, TodoType.FERTILIZERING, "", false, LocalDateTime.now().plusDays(6), null));
        farmTodoRepository.save(new FarmTodo(farmMainPage2, TodoType.NATURE, "호우주의보", false, LocalDateTime.now(), null));
    }

    private void createCommunityTagSample() {
        for (String tagName : tagNames) {
            communityTagRepository.save(new CommunityTag(tagName));
        }
    }

    private void createCommunitySample() {
        Random rand = new Random();

        List<User> users = userRepository.findAll();
        List<CommunityTag> communityTags = communityTagRepository.findAll();
        int tagNameSize = communityTags.size();

        // 회원에 랜덤으로 2~5개씩 선호태그 부여
        for (User u : users) {
            int tagCount = rand.nextInt(4) + 2;
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < tagCount; i++) {
                int tagId = getTagId(rand, tagNameSize, set);
                communityFavoriteTagRepository.save(new CommunityFavoriteTag(u, communityTags.get(tagId)));
            }
        }

        // 커뮤니티 글 작성(with 이미지, 선택된 태그, 좋아요, 댓글)
        for (int i = 0; i < communityTitleAndContent.length; i++) {
            String title = communityTitleAndContent[i][0];
            String content = communityTitleAndContent[i][1];
            int userId = rand.nextInt(13);
            Community saveCommunity = communityRepository.save(new Community(users.get(userId), title, content, dateUtil.generateRandomDateTime(2023, 1, 1)));

            // 이미지 개수 (1~3개)
            int imgCount = rand.nextInt(3) + 1;
            for (int j = 0; j < imgCount; j++) {
                communityImageRepository.save(new CommunityImage(saveCommunity, "community" + (rand.nextInt(50) + 1) + ".png"));
            }

            // 선택된 태그(5개)
            int selectedTagCount = 5;
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < selectedTagCount; j++) {
                int tagId = getTagId(rand, tagNameSize, set);
                communitySelectedTagRespository.save(new CommunitySelectedTag(saveCommunity, communityTags.get(tagId)));
            }

            // 좋아요
            int heartCount = rand.nextInt(13) + 1;
            boolean isSeqFirst = rand.nextBoolean();
            for (int j = 0; j < heartCount; j++) {
                LocalDateTime writeDate = saveCommunity.getWriteDate();
                if (isSeqFirst) {
                    communityHeartRepository.save(new CommunityHeart(saveCommunity, users.get(j),
                            dateUtil.generateRandomDateTime(writeDate.getYear(), writeDate.getMonthValue(), writeDate.getDayOfMonth())));
                } else {
                    communityHeartRepository.save(new CommunityHeart(saveCommunity, users.get(12 - j),
                            dateUtil.generateRandomDateTime(writeDate.getYear(), writeDate.getMonthValue(), writeDate.getDayOfMonth())));
                }
            }

            // 댓글
            int commentSize = communityComments[i].length;
            for (int j = 0; j < commentSize; j++) {
                int userCommentId = rand.nextInt(13);
                communityCommentRepository.save(new CommunityComment(saveCommunity, users.get(userCommentId), communityComments[i][j]));
            }
        }
    }

    private static int getTagId(Random rand, int tagNameSize, Set<Integer> set) {
        int tagId = rand.nextInt(tagNameSize);
        while(true) {
            if(!set.contains(tagId)) {
                set.add(tagId);
                break;
            }
            tagId = rand.nextInt(tagNameSize);
        }
        return tagId;
    }


}
