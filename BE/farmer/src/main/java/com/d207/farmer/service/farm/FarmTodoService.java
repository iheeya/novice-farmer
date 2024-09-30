package com.d207.farmer.service.farm;

import com.d207.farmer.repository.farm.FarmTodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FarmTodoService {
    private final FarmTodoRepository farmTodoRepository;
}
