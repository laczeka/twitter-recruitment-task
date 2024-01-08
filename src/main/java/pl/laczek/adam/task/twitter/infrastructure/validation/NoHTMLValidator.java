package pl.laczek.adam.task.twitter.infrastructure.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class NoHTMLValidator implements ConstraintValidator<NoHtmlInjection,String>
{
    @Override
    public void initialize(NoHtmlInjection constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Jsoup.isValid(s, Safelist.none());
    }
}