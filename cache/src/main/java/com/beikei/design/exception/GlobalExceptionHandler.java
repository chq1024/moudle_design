package com.beikei.design.exception;

import io.lettuce.core.support.caching.CacheFrontend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CacheFrontend.ValueRetrievalException.class)
    public Map<String,Object> handleValueRetrievalException(CacheFrontend.ValueRetrievalException e) {
        String message = e.getMessage();
        return Map.of("code",500,"message",message);
    }

    @ExceptionHandler(RuntimeException.class)
    @SuppressWarnings("all")
    public Map<String,Object> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        String message = e.getMessage();
        return Map.of("code",500,"message",message);
    }
}
