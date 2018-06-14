package com.antonberezin.spacevalidationexamples.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Constraint(
        validatedBy = OneOf.Validator.class
)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(OneOf.List.class)
public @interface OneOf {

    String[] values();

    String message() default "{OneOf.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        OneOf[] value();
    }

    class Validator implements ConstraintValidator<OneOf, String> {
        private Set<String> values;

        public void initialize(OneOf constraint) {
            values = new HashSet<>(Arrays.asList(constraint.values()));
        }

        public boolean isValid(String value, ConstraintValidatorContext context) {
            return StringUtils.isBlank(value) || values.contains(value);
        }
    }
}
