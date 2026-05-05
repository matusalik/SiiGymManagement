package org.sii.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MemberAlreadyInactiveException extends RuntimeException {
    public MemberAlreadyInactiveException(String message) {
        super(message);
    }
}
