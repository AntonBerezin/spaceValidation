package com.antonberezin.spacevalidationexamples.infra;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;

@Getter
public class SpaceValidationException extends Exception {

    private Map<String, String> errors;

    public SpaceValidationException(Map<String, String> errors) {
        super("validation exception");
        this.errors = Collections.unmodifiableMap(errors);
    }
}
