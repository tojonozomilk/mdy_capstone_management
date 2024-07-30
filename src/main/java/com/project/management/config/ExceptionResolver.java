package com.project.management.config;

import com.project.management.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @description Global Exception Handler
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Slf4j
@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(Throwable.class)
    public Result<String> handleException(Throwable e) {
        log.error(e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public Result<String> error(Exception e) {
        log.error(e.getMessage(), e);
        StackTraceElement traceElement = e.getStackTrace()[0];
        String message = traceElement.toString();
        return Result.failed(message + ": " + e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Result<String> error(BindException e) {
        String errorMessage = e.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .collect(Collectors.joining(","));
        return Result.failed(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> error(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .collect(Collectors.joining(","));
        return Result.failed(errorMessage);
    }
}