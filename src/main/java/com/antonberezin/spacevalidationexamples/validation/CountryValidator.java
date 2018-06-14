package com.antonberezin.spacevalidationexamples.validation;

import com.antonberezin.spacevalidationexamples.services.SpaceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CountryValidator implements ConstraintValidator<Country, String> {

    private SpaceService spaceService;

    @Autowired
    public CountryValidator(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || spaceService.countryExists(value);
    }
}
