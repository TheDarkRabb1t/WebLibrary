package tdr.pet.weblibrary.model.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class TestIsbnValidator {
    private IsbnValidator isbnValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        isbnValidator = new IsbnValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void whenValidIsbn10_thenValidationPasses() {
        assertTrue(isbnValidator.isValid("0306406152", context));
        assertTrue(isbnValidator.isValid("1566199093", context));
        assertTrue(isbnValidator.isValid("0471117099", context));
    }

    @Test
    void whenInvalidIsbn10_thenValidationFails() {
        assertTrue(isbnValidator.isValid("123456789X", context));
        assertFalse(isbnValidator.isValid("047195869X", context));
        assertFalse(isbnValidator.isValid("0306406153", context));
    }

    @Test
    void whenValidIsbn13_thenValidationPasses() {
        assertTrue(isbnValidator.isValid("9780306406157", context));
        assertTrue(isbnValidator.isValid("9783161484100", context));
        assertTrue(isbnValidator.isValid("9780471117094", context));
    }

    @Test
    void whenInvalidIsbn13_thenValidationFails() {
        assertFalse(isbnValidator.isValid("9780306406156", context));
        assertFalse(isbnValidator.isValid("9783161484101", context));
        assertFalse(isbnValidator.isValid("9780471117095", context));
    }

    @Test
    void whenIsbnIsEmptyOrNull_thenValidationFails() {
        assertFalse(isbnValidator.isValid(null, context));
        assertFalse(isbnValidator.isValid("", context));
    }

    @Test
    void whenIsbnHasInvalidLength_thenValidationFails() {
        assertFalse(isbnValidator.isValid("123", context));
        assertFalse(isbnValidator.isValid("12345678901234", context));
    }

    @Test
    void whenIsbnContainsNonDigits_thenValidationFails() {
        assertFalse(isbnValidator.isValid("97803064061A7", context));  // Non-digit character
        assertFalse(isbnValidator.isValid("978030640@157", context));  // Non-digit character
    }
}
