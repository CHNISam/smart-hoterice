package com.smartrice.core.config;

import com.smartrice.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgument(IllegalArgumentException e) {
        logger.warn("Bad argument: {}", e.getMessage());
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        logger.error("Internal error", e);
        return ResponseUtil.serious();
    }
}
