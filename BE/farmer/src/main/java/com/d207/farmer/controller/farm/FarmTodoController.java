package com.d207.farmer.controller.farm;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "TODO 조회", description = "todo")
public class FarmTodoController {
}
