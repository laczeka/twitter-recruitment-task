package pl.laczek.adam.task.twitter.infrastructure.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoHTMLValidator.class)
public @interface NoHtmlInjection {
    String message() default "Unsafe HTML tags included";
    Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}
