package com.antonberezin.spacevalidationexamples.web;

import com.antonberezin.spacevalidationexamples.infra.SpaceValidationException;
import com.antonberezin.spacevalidationexamples.services.SpaceService;
import com.antonberezin.spacevalidationexamples.services.ValidationHandler;
import com.antonberezin.spacevalidationexamples.web.dto.JobApplication;
import com.antonberezin.spacevalidationexamples.web.dto.SaveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SpaceController {

    private SpaceService spaceService;

    private ValidationHandler validationHandler;

    @Autowired
    public SpaceController(SpaceService spaceService, ValidationHandler validationHandler) {
        this.spaceService = spaceService;
        this.validationHandler = validationHandler;
    }

    @PostMapping("/job-applications/")
    @ResponseStatus(HttpStatus.CREATED)
    public SaveResponse postGeneralData(@RequestBody JobApplication request) throws SpaceValidationException {
        Map<String, String> errors = validationHandler.validate(request);
        if (!errors.isEmpty()) {
            throw new SpaceValidationException(errors);
        }
        return new SaveResponse(spaceService.generateId());
    }
}
