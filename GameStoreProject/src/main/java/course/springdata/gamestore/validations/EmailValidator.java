package course.springdata.gamestore.validations;

import course.springdata.gamestore.validations.annotations.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email,String> {
    @Override
    public void initialize(Email constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.contains("@") && s.contains(".");
    }
}
