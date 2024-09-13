package com.d207.farmer.exception.advice;

import com.d207.farmer.dto.exception.ErrorResponseDTO;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
//@RestControllerAdvice(assignableTypes = {UserController.class}) // 대상 컨트롤러 지정 가능
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnexpectedTypeException.class)
    public ErrorResponseDTO UnexpectedTypeExHandle(UnexpectedTypeException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponseDTO("BAD_REQUEST", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDTO MethodArgumentNotValidExHandle(MethodArgumentNotValidException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponseDTO("BAD_REQUEST", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponseDTO HttpMessageNotReadableExHandle(HttpMessageNotReadableException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponseDTO("BAD_REQUEST", e.getMessage());
    }
}
