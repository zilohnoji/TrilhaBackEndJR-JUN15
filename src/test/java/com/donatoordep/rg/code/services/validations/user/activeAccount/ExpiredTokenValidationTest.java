package com.donatoordep.rg.code.services.validations.user.activeAccount;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationExpiredException;
import com.donatoordep.rg.code.exceptions.ONBEntityNotFoundException;
import com.donatoordep.rg.code.factory.EmailCodeConfirmationFactory;
import com.donatoordep.rg.code.services.validations.user.activeAccount.validations.ExpiredTokenValidation;
import com.donatoordep.rg.code.util.ApplicationConfigValidationTest;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.LocalDateTime;

@DisplayName("ExpiredTokenValidation - Expired Token Validation Test")
public class ExpiredTokenValidationTest extends ApplicationConfigValidationTest {

    static ExpiredTokenValidation expiredTokenValidation;
    static EmailCodeConfirmation expiredCode;
    static EmailCodeConfirmation validCode;
    static String invalidCode;

    @BeforeAll
    static void setupAll() {
        invalidCode = "";
        expiredCode = EmailCodeConfirmationFactory.createCode(LocalDateTime.now().minusDays(10));
        validCode = EmailCodeConfirmationFactory.createCode();
        expiredTokenValidation = new ExpiredTokenValidation();
    }

    @BeforeEach
    void setup() {
        Mockito.when(this.emailCodeConfirmationRepository.findByTokenOrThrowNotExists(invalidCode)).thenThrow(ONBEntityNotFoundException.class);
        Mockito.when(this.emailCodeConfirmationRepository.findByTokenOrThrowNotExists(validCode.getCode())).thenReturn(validCode);
        Mockito.when(this.emailCodeConfirmationRepository.findByTokenOrThrowNotExists(expiredCode.getCode())).thenReturn(expiredCode);
    }

    @Test
    void validateShouldDoNothingWhenCodeIsValid() {
        Assertions.assertDoesNotThrow(() -> {
            expiredTokenValidation.validate(new UserActiveAccountArgs(validCode.getCode(), this.emailCodeConfirmationRepository));
        }, "Expected nothing exception, but throw exception");
    }

    @Nested
    @DisplayName("TokenValidation Class Fail Test")
    public class ExpiredTokenValidationFailTest {
        @Test
        void validateShouldThrowONBEmailCodeConfirmationExpiredExceptionWhenCodeHasExpired() {
            Assertions.assertThrows(ONBEmailCodeConfirmationExpiredException.class, () -> {
                expiredTokenValidation.validate(new UserActiveAccountArgs(expiredCode.getCode(), emailCodeConfirmationRepository));
            }, "Expected throw ONBEmailCodeConfirmationExpiredException, but throw nothing exception");
        }

        @Test
        void validateShouldThrowONBEntityNotFoundExceptionWhenCodeNotIsValid() {
            Assertions.assertThrows(ONBEntityNotFoundException.class, () -> {
                expiredTokenValidation.validate(new UserActiveAccountArgs(invalidCode, emailCodeConfirmationRepository));
            }, "Expected throw ONBEntityNotFoundException, but throw nothing exception");
        }
    }
}