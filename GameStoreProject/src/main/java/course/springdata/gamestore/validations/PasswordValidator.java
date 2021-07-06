package course.springdata.gamestore.validations;

import course.springdata.gamestore.validations.annotations.Email;
import course.springdata.gamestore.validations.annotations.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password,String> {

    @Override
    public void initialize(Password constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length()>=6 && s.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    }
}
