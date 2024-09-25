package com.d207.farmer.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class DateUtil {
    public String timeStampToYmd(LocalDateTime date) {
        return date.toString().substring(0, 11);
    }
}
