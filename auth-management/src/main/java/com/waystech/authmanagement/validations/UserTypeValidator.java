package com.waystech.authmanagement.validations;

import com.waystech.authmanagement.exceptions.classes.CustomValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
