package course.springdata.gamestore.validations.annotations;

import course.springdata.gamestore.validations.SizeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SizeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD,ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {
    String message() default "Invalid size";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
