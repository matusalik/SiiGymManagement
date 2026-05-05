package org.sii.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GymAlreadyExistsException extends RuntimeException {
    public GymAlreadyExistsException(String message) {
        super(message);
    }
}
