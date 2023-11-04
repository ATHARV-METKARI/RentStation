package com.renstation.common.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OTP {
    String message() default "Invalid OTP";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
