package com.antonberezin.spacevalidationexamples.validation;

import com.antonberezin.spacevalidationexamples.infra.util.SpaceUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Objects;

@Constraint(
        validatedBy = ConditionalNotNull.Validator.class
)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ConditionalNotNull.List.class)
public @interface ConditionalNotNull {

    String field();

    String conditionField();

    String conditionValue();

    String message() default "{ConditionalNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        ConditionalNotNull[] value();
    }

    class Validator implements ConstraintValidator<ConditionalNotNull, Object> {

        private String field;
        private String conditionField;
        private String conditionValue;

        public void initialize(ConditionalNotNull constraint) {
            field = constraint.field();
            conditionField = constraint.conditionField();
            conditionValue = constraint.conditionValue();
        }

        public boolean isValid(Object object, ConstraintValidatorContext context) {
            String conditionFieldValue = SpaceUtils.getStringField(object, conditionField);
            Object fieldValue = SpaceUtils.getField(object, field);
            boolean isValid = !StringUtils.equals(conditionValue, conditionFieldValue)
                    || Objects.nonNull(fieldValue);
            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(field)
                        .addConstraintViolation();
            }
            return isValid;
        }
    }
}
