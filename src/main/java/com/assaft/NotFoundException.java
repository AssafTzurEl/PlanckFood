package com.assaft;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Food ID not found: " + id);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
