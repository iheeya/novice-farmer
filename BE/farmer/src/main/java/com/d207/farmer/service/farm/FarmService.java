package com.d207.farmer.service.farm;

import com.d207.farmer.domain.common.Address;
import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.farm.register.FarmRegisterRequestDTO;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.farm.UserPlaceRepository;
import com.d207.farmer.repository.place.PlaceRepository;
import com.d207.farmer.repository.plant.PlantRepository;
import com.d207.farmer.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.StringTokenizer;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepository farmRepository;
    private final PlaceRepository placeRepository;
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;
    private final UserPlaceRepository userPlaceRepository;

    @Transactional
    public String registerFarm(Long userId, FarmRegisterRequestDTO request) {
        // user, place, plant 조회
        User user = userRepository.findById(userId).orElseThrow();
        Place place = placeRepository.findById(request.getPlace().getPlaceId()).orElseThrow();
        Plant plant = plantRepository.findById(request.getPlant().getPlantId()).orElseThrow();

        // userPlace 생성 및 저장
        UserPlace userPlace = createUserPlace(user, place, request);
        UserPlace saveUserPlace = userPlaceRepository.save(userPlace);

        // farm 생성
        Farm farm = new Farm(user, saveUserPlace, plant, request.getPlant());
        farmRepository.save(farm);

        return "농장 생성 완료";
    }

    private UserPlace createUserPlace(User user, Place place, FarmRegisterRequestDTO request) {
        // 위 경도 불러오기
        // TODO 주소로 위경도 찾기
        String latitude = "";
        String longitude = "";

        Address address = request.getPlace().getAddress();
        // 내장 값타입은 값 변경할 때 생성자로 인스턴스 새로 생성
        // 지번에서 번지 찾아서 address 인스턴스 재생성
        Address newAddress = createAddress(address);

        // userPlace 생성
        return new UserPlace(user, place, latitude, longitude, place.getName(), newAddress, request.getPlace().getDirection());
    }

    private Address createAddress(Address addr) {
        // 번지는 지번의 마지막 토큰 값으로 넣기
        // 우편번호 api에서 번지는 제공해주지 않음
        // 대신 띄어쓰기 형식이 핏하게 맞아서 토크나이저로 마지막 토큰만 가져오면 됨

        String jibun = addr.getJibun();
        StringTokenizer st = new StringTokenizer(jibun, " ");
        String bunji = "";
        while(st.hasMoreTokens()) {
            bunji = st.nextToken();
        }
        return new Address(addr.getSido(), addr.getSigugun(), addr.getBname1(), addr.getBname2(),
                bunji, addr.getJibun(), addr.getZonecode());
    }

}
