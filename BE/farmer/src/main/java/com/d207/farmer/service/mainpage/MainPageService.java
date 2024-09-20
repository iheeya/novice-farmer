package com.d207.farmer.service.mainpage;

import com.d207.farmer.dto.mainpage.MainPageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageService {

    public MainPageResponseDTO getMainPage(Long userId) {
        // TODO
        return null;
    }
}
