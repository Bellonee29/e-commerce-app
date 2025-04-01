package org.partypal.commonModule.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.partypal.commonModule.exceptions.classes.CustomValidationException;

public class UserTypeValidator implements ConstraintValidator<ValidUserType, String> {
    @Override
    public boolean isValid(String userType, ConstraintValidatorContext constraintValidatorContext) {
        if(userType != null && (userType.equalsIgnoreCase
                ("user") || userType.equalsIgnoreCase("promoter"))){
            return true;
        }else {
            throw new CustomValidationException("Invalid UserType");
        }
    }
}
