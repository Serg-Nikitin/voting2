package ru.javaops.topjava2.util.validation.nohtml;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import ru.javaops.topjava2.util.validation.nohtml.NoHtml;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoHtmlValidator implements ConstraintValidator<NoHtml, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        return value == null || Jsoup.clean(value, Safelist.none()).equals(value);
    }
}
