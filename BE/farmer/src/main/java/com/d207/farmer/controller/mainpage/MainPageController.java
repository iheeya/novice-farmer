package com.d207.farmer.controller.mainpage;

import com.d207.farmer.dto.mainpage.MainPageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mainpage")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainPageController {

    @GetMapping
    public ResponseEntity<MainPageResponseDTO> getMainPage(@RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok().body(new MainPageResponseDTO());
    }
}
