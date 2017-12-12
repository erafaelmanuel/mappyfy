package io.ermdev.mapfierj;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotRawValidator implements ConstraintValidator<NotRaw, String> {
    @Override
    public void initialize(NotRaw notRaw) {}

    @Override
    public boolean isValid(String obj, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
