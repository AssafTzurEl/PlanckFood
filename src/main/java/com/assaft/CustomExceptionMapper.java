package com.assaft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionMapper {

    @ExceptionHandler
    public ResponseEntity<String> handleBadRequestException(
            IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getLocalizedMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleAllOtherErrors(Exception exception) {
        logger.info(exception.getStackTrace().toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getLocalizedMessage());
    }

    Logger logger = LoggerFactory.getLogger(CustomExceptionMapper.class);
}

