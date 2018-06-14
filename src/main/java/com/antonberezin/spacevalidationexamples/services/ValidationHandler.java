package com.antonberezin.spacevalidationexamples.services;

import com.antonberezin.spacevalidationexamples.validation.groups.Captain;
import com.antonberezin.spacevalidationexamples.validation.groups.Pilot;
import com.antonberezin.spacevalidationexamples.web.dto.JobApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationHandler {

    private Validator validator;

    @Autowired
    public ValidationHandler(Validator validator) {
        this.validator = validator;
    }

    public  Map<String, String> validate(JobApplication jobApplication) {
        Set<ConstraintViolation<JobApplication>> violations =
                validator.validate(jobApplication, getGroup(jobApplication));
        return violations
                .stream()
                .collect(Collectors.toMap(violation -> violation.getPropertyPath().toString()
                        , ConstraintViolation::getMessage
                        , (key1, key2) -> key1));
    }

    private Class<?> getGroup(JobApplication jobApplication) {
        return "Captain".equals(jobApplication.getPosition()) ? Captain.class : Pilot.class;
    }
}

