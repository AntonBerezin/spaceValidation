package com.antonberezin.spacevalidationexamples.web;

import com.antonberezin.spacevalidationexamples.infra.SpaceValidationException;
import com.antonberezin.spacevalidationexamples.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(SpaceValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handlesValidationException(SpaceValidationException ex) {
        return new ErrorResponse(ex.getErrors());
    }
}
