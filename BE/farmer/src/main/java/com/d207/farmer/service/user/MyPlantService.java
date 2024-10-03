package com.d207.farmer.service.user;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.FarmTodo;
import com.d207.farmer.domain.farm.TodoType;
import com.d207.farmer.domain.plant.PlantGrowthIllust;
import com.d207.farmer.domain.plant.PlantThreshold;
import com.d207.farmer.dto.myplant.*;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.farm.FarmTodoRepository;
import com.d207.farmer.utils.DateUtil;
import com.d207.farmer.utils.FastApiUtil;
import com.d207.farmer.utils.UserAuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPlantService {

    private final FarmRepository farmRepository;
    private final FarmTodoRepository farmTodoRepository;
    private final FastApiUtil fastApiUtil;
    private final DateUtil dateUtil;
    private final UserAuthUtil userAuthUtil;

    @Transactional
    public String startGrowPlant(Long userId, StartGrowPlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        userAuthUtil.authorizationUser(userId, farm); // 회원 일치 여부
        farm.startGrow();
        return "작물 키우기 시작하기 성공";
    }

    @Transactional
    public String deletePlant(Long userId, ManagePlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        userAuthUtil.authorizationUser(userId, farm); // 회원 일치 여부
        farm.delete();
        return "작물 삭제 성공";
    }

    @Transactional
    public String harvestPlant(Long userId, ManagePlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        userAuthUtil.authorizationUser(userId, farm); // 회원 일치 여부
        farm.harvest();
        return "작물 첫수확 성공";
    }

    @Transactional
    public String endPlant(Long userId, ManagePlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        userAuthUtil.authorizationUser(userId, farm); // 회원 일치 여부
        farm.end();
        return "작물 키우기 종료하기 성공";
    }

    @Transactional
    public String waterPlant(Long userId, ManagePlantRequestDTO request) {
        List<FarmTodo> farmTodos = farmTodoRepository.findByFarmIdAndIsCompletedFalseAndTodoType(request.getFarmId(), TodoType.WATERING);
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow(); // MVP 끝나면 아래 if문 안에 넣기
        userAuthUtil.authorizationUser(userId, farm); // 회원 일치 여부
        if(farmTodos == null || farmTodos.isEmpty()) { // todo가 없으면 임의 생성인데 그럴 일 있나
//            Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
            farmTodoRepository.save(new FarmTodo(farm, TodoType.WATERING, true, null, LocalDateTime.now()));
            return "작물 물주기 성공(todo 생성)";
        }
        farmTodos.get(0).updateTodoComplete();

        // TODO MVP 발표용 FAST와 통신 단절 후 임의로 칼럼 추가
        farmTodoRepository.save(new FarmTodo(farm, TodoType.WATERING, false, LocalDateTime.now(), null));

        return "작물 물주기 성공(todo 업데이트)";
    }

    @Transactional
    public String fertilizerPlant(Long userId, ManagePlantRequestDTO request) {
        List<FarmTodo> farmTodos = farmTodoRepository.findByFarmIdAndIsCompletedFalseAndTodoType(request.getFarmId(), TodoType.FERTILIZERING);
        if(farmTodos == null || farmTodos.isEmpty()) {
            Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
            userAuthUtil.authorizationUser(userId, farm); // 회원 일치 여부
            farmTodoRepository.save(new FarmTodo(farm, TodoType.FERTILIZERING, true, null, LocalDateTime.now()));
            return "작물 비료주기 성공(todo 생성)";
        }
        farmTodos.get(0).updateTodoComplete();
        return "작물 비료주기 성공(todo 업데이트)";
    }

    @Transactional
    public String updateName(Long userId, UpdatePlantNameRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        userAuthUtil.authorizationUser(userId, farm); // 회원 일치 여부
        farm.updateName(request.getPlantName());
        return "이름 변경 성공";
    }

    @Transactional
    public String updateMemo(Long userId, UpdatePlantMemoRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        userAuthUtil.authorizationUser(userId, farm); // 회원 일치 여부
        farm.updateMemo(request.getMemo());
        return "메모 변경 성공";
    }

    public InspectionPestResponseDTO inspectionPest(Long userId, InspectionPlantRequestDTO request) {
        InspectionPestResponseByFastApiDTO response = fastApiUtil.getInspectionPest(request.getImagePath());
        // TODO response에서 병해충 이름 받아서 몽고db 조회 후 반환
        if(!response.getHasPast()) {
            InspectionPestResponseDTO.IsPestDTO isPestDTO = new InspectionPestResponseDTO.IsPestDTO(false, request.getImagePath());
            InspectionPestResponseDTO.PestInfoDTO pestInfoDTO = new InspectionPestResponseDTO.PestInfoDTO();
            return new InspectionPestResponseDTO(isPestDTO, pestInfoDTO);
        }
        InspectionPestResponseDTO.IsPestDTO isPestDTO = new InspectionPestResponseDTO.IsPestDTO(true, request.getImagePath());
        InspectionPestResponseDTO.PestInfoDTO pestInfoDTO = new InspectionPestResponseDTO.PestInfoDTO(response.getPestInfo().getPestImagePath(),
                response.getPestInfo().getPestName(), response.getPestInfo().getPestDesc(), response.getPestInfo().getPestCureDesc());

        return new InspectionPestResponseDTO(isPestDTO, pestInfoDTO);
    }

    public InspectionGrowthStepResponseDTO inspectionGrowthStep(Long userId, InspectionPlantRequestDTO request) {
        InspectionGrowthStepResponseByFastApiDTO response = fastApiUtil.getInspectionGrowthStep(request.getImagePath());
        return null;
    }

//    @Transactional
//    public String updateGrowthStepByInspection(Long userId, UpdateDegreeDayRequestDTO request) {
//        Farm farm = farmRepository.findById(request.getMyPlantId()).orElseThrow();
//        farm.updateDegreeDay(request.getDegreeDay());
//        return "생장단계 업데이트 성공";
//    }

    @Transactional // TODO MVP 끝나면 지워야함
    public MyPlantInfoResponseDTO getMyPlantInfo(Long userId, Long myPlantId) {
        Farm farm = farmRepository.findByIdWithJoin(myPlantId).orElseThrow();
        userAuthUtil.authorizationUser(userId, farm); // 회원 일치 여부
        if(farm.getSeedDate() == null) {
            return new MyPlantInfoResponseDTO(false, false, new MyPlantInfoResponseDTO.PlantInfoDTO(), new ArrayList<>());
        }

        // 작물 growthStep 계산
        int growthStep = 1;
        int maxDegreeDay = farm.getPlant().getDegreeDay();
        for(PlantThreshold pt : farm.getPlant().getPlantThresholds()) {
            if(farm.getDegreeDay() < pt.getDegreeDay()) break;
            growthStep++;
        }
        if(farm.getDegreeDay() == maxDegreeDay) growthStep++;

        // 일러스트 이미지 경로
        String imagePath = "";
        for (PlantGrowthIllust pgi : farm.getPlant().getPlantGrowthIllusts()) {
            if(pgi.getStep() == growthStep) {
                imagePath = pgi.getImagePath();
                break;
            }
        }

        List<FarmTodo> farmTodos = farmTodoRepository.findByFarmIdAndIsCompletedFalse(myPlantId);
        // remain day 0보다 아래면 안넣기
        List<MyPlantInfoResponseDTO.TodoInfoDTO> todoInfoDTOs = new ArrayList<>();
        int todoCnt = 0;
        for (FarmTodo ft : farmTodos) {
            if(++todoCnt > 2) break;
            if(ft.getTodoDate().isBefore(LocalDateTime.now())) continue;
            int remainDay = ft.getTodoDate().getDayOfYear() - LocalDateTime.now().getDayOfYear();
            if(remainDay < 0) { // 내년 넘어갈 때
                remainDay = ft.getTodoDate().getDayOfYear() + 365 - LocalDateTime.now().getDayOfYear();
            }
            todoInfoDTOs.add(new MyPlantInfoResponseDTO.TodoInfoDTO(dateUtil.timeStampToYmd(ft.getTodoDate()),
                    ft.getTodoType(), remainDay));
        }

        List<PlantThreshold> plantThresholds = farm.getPlant().getPlantThresholds();
        MyPlantInfoResponseDTO.ThresholdDTO thresholdDTO = new MyPlantInfoResponseDTO.ThresholdDTO(
                plantThresholds.size() + 1,
                dateUtil.degreeDayToRatio(farm.getPlant().getPlantThresholds().get(0).getDegreeDay(), farm.getPlant().getDegreeDay()),
                dateUtil.degreeDayToRatio(farm.getPlant().getPlantThresholds().get(1).getDegreeDay(), farm.getPlant().getDegreeDay()),
                plantThresholds.size() == 3 ?
                        dateUtil.degreeDayToRatio(farm.getPlant().getPlantThresholds().get(2).getDegreeDay(), farm.getPlant().getDegreeDay()) : null);

        List<FarmTodo> farmTodosIsCompleted = farmTodoRepository.findByFarmIdAndIsCompletedTrue(myPlantId, TodoType.WATERING, TodoType.FERTILIZERING);

        // To Do의 최근 물주기 날짜구하기
        String recentWateringDate = "";
        for (FarmTodo ft : farmTodosIsCompleted) {
            if(ft.getTodoType().equals(TodoType.WATERING)) {
                recentWateringDate = dateUtil.timeStampToYmd(ft.getTodoDate());
                break;
            }
        }

        // To Do의 최근 비료주기 날짜구하기
        String recentFertilizingDate = "";
        for (FarmTodo ft : farmTodosIsCompleted) {
            if(ft.getTodoType().equals(TodoType.FERTILIZERING)) {
                recentFertilizingDate = dateUtil.timeStampToYmd(ft.getTodoDate());
                break;
            }
        }

        MyPlantInfoResponseDTO.PlantInfoDTO plantInfo = new MyPlantInfoResponseDTO.PlantInfoDTO(
                farm.getUserPlace().getPlace().getName(), farm.getUserPlace().getId(), farm.getUserPlace().getName(),
                farm.getPlant().getName(), farm.getMyPlantName(), imagePath, dateUtil.timeStampToYmd(farm.getSeedDate()),
                growthStep, dateUtil.degreeDayToRatio(farm.getDegreeDay(), farm.getPlant().getDegreeDay()), thresholdDTO,
                farm.getIsFirstHarvest() ? dateUtil.timeStampToYmd(farm.getFirstHarvestDate()) : "",
                recentWateringDate, recentFertilizingDate, farm.getMemo());

        // TODO MVP용 degreeDay update -> mvp 끝나면 지워야함
        farm.updateDegreeDay(farm.getDegreeDay() + 90);
        if(farm.getDegreeDay() > 1960) farm.updateDegreeDay(1960);
        /////////////////////////////////////////////////

        return new MyPlantInfoResponseDTO(true, farm.getIsFirstHarvest(), plantInfo, todoInfoDTOs);
    }

    public String updateTodoByFastApi(Long farmId, TodoType todoType) {
        return fastApiUtil.updateTodo(farmId, todoType);
    }

    @Transactional
    public String updateMyPlantDegreeDay(Long myPlantId, UpdateDegreeDayRequestDTO request) {
        Farm farm = farmRepository.findById(myPlantId).orElseThrow();
        farm.updateDegreeDay(request.getDegreeDay());
        return "degreeDay Update 완료";
    }
}
