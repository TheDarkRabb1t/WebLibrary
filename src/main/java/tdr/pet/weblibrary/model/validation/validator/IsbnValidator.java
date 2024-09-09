package tdr.pet.weblibrary.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
import tdr.pet.weblibrary.model.validation.annotation.ValidIsbn;

public class IsbnValidator implements ConstraintValidator<ValidIsbn, String> {
    @Override
    public void initialize(ValidIsbn constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.hasText(isbn)) {
            return isValidIsbn10(isbn) || isValidIsbn13(isbn);
        }
        return false;
    }

    private boolean isValidIsbn10(String isbn) {
        if (isbn.length() != 10) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Character.getNumericValue(isbn.charAt(i));
                if (digit < 0 || digit > 9) {
                    return false;
                }
                sum += (digit * (10 - i));
            }
            char lastChar = isbn.charAt(9);
            sum += (lastChar == 'X' ? 10 : Character.getNumericValue(lastChar));

            return sum % 11 == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidIsbn13(String isbn) {
        if (isbn.length() != 13) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                int digit = Character.getNumericValue(isbn.charAt(i));
                if (digit < 0 || digit > 9) {
                    return false;
                }
                sum += digit * (i % 2 == 0 ? 1 : 3);
            }
            int lastDigit = Character.getNumericValue(isbn.charAt(12));
            return (sum + lastDigit) % 10 == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
