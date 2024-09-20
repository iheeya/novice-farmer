package com.d207.farmer.controller.user;

import com.d207.farmer.service.user.MyPlaceService;
import com.d207.farmer.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/myplace")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MyPlaceController {

    private final MyPlaceService myPlaceService;
    private final JWTUtil jwtUtil;

}
