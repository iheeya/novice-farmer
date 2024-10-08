package com.d207.farmer.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Slf4j
@Component
public class DateUtil {

    public String getTime(LocalDateTime writeDate) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(writeDate, now);
        long hours = ChronoUnit.HOURS.between(writeDate, now);
        long days = ChronoUnit.DAYS.between(writeDate, now);
        long months = ChronoUnit.MONTHS.between(writeDate, now);
        long years = ChronoUnit.YEARS.between(writeDate, now);

        if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else if (days < 30) {
            return days + "일 전";
        } else if (months < 12) {
            return months + "개월 전";
        } else {
            return years + "년 전";
        }
    }

    public int degreeDayToRatio(int degreeDay, int maxDegreeDay) {
        if(degreeDay >= maxDegreeDay) return 100;
        return (int) ((double) degreeDay / maxDegreeDay * 100);
    }

    public LocalDateTime generateRandomDateTime(int startYear, int startMonth, int startDay) {
        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
        LocalDate endDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        Random random = new Random();
        long randomDays = random.nextInt((int) daysBetween + 1);
        LocalDate randomDate = startDate.plusDays(randomDays);

        int randomHour = random.nextInt(24);
        int randomMinute = random.nextInt(60);
        int randomSecond = random.nextInt(60);

        return randomDate.atTime(randomHour, randomMinute, randomSecond);
    }

}
