package course.springdata.gamestore.validations;

import course.springdata.gamestore.validations.annotations.Size;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class SizeValidator implements ConstraintValidator<Size, BigDecimal> {
    @Override
    public void initialize(Size constraintAnnotation) {

    }

    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext constraintValidatorContext) {
        return bigDecimal.floatValue()>0;
    }
}
