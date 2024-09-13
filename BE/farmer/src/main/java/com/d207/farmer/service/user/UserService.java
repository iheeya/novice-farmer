package com.d207.farmer.service.user;

import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.plant.PlantResponseDTO;
import com.d207.farmer.dto.user.*;
import com.d207.farmer.exception.FailedAuthenticateUserException;
import com.d207.farmer.repository.user.UserRepository;
import com.d207.farmer.service.plant.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PlantService plantService;

    @Transactional
    public UserInfoResponseDTO registerUser(UserRegisterRequestDTO request) {
        User user = new User(request);
        User saveUser = userRepository.save(user);
        return UserInfoResponseDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .regDate(user.getRegDate())
                .isFirstLogin(user.getIsFirstLogin())
                .gender(user.getGender())
                .age(user.getAge())
                .address(user.getAddress())
                .build();
    }

    public UserInfoResponseDTO getUserInfo(UserInfoRequestByEmailDTO request) {
        User user = userRepository.findByEmail(request.getEmail());
        return UserInfoResponseDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .regDate(user.getRegDate())
                .isFirstLogin(user.getIsFirstLogin())
                .gender(user.getGender())
                .age(user.getAge())
                .address(user.getAddress())
                .build();
    }

    public UserInfoResponseDTO getUserInfo(Long userId) {
        Optional<User> optUser = userRepository.findById(userId);
        User user = optUser.orElseThrow();
        return UserInfoResponseDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .regDate(user.getRegDate())
                .isFirstLogin(user.getIsFirstLogin())
                .gender(user.getGender())
                .age(user.getAge())
                .address(user.getAddress())
                .build();
    }

    @Transactional
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO request) {
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        boolean check_firstLogin = user.getIsFirstLogin();
        if(check_firstLogin){
            user.setIsFirstLogin(false);
        }

        if(user == null) {
            throw new FailedAuthenticateUserException("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }
        return tokenService.saveRefreshToken(user.getId(),check_firstLogin);
    }





    public List<PlantResponseDTO> surveyplant(){
        List<PlantResponseDTO> plantResponseDTOList = plantService.getAllPlants();
        // UserSurveyResponseDTO를 생성하는 로직 추가
        plantResponseDTOList.add(new PlantResponseDTO((long)0,"없음",0,false));

        // id 기준으로 정렬
        plantResponseDTOList.sort(Comparator.comparing(PlantResponseDTO::getId));

        return plantResponseDTOList; // 적절한 값을 전달
    }
}
