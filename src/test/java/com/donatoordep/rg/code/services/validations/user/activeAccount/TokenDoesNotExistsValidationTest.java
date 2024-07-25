//package com.donatoordep.rg.code.services.validations.user.activeAccount;
//
//import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
//import com.donatoordep.rg.code.exceptions.ONBEntityNotFoundException;
//import com.donatoordep.rg.code.factory.EmailCodeConfirmationFactory;
//import com.donatoordep.rg.code.services.validations.user.activeAccount.validations.TokenDoesNotExistsValidation;
//import com.donatoordep.rg.code.util.ApplicationConfigValidationTest;
//import org.junit.jupiter.api.*;
//import org.mockito.Mockito;
//
//@DisplayName("TokenDoesNotExistsValidation - Token Does Not Exists Validation Test")
//public class TokenDoesNotExistsValidationTest extends ApplicationConfigValidationTest {
//
//    static String invalidCode;
//    static EmailCodeConfirmation validCode;
//    static TokenDoesNotExistsValidation tokenDoesNotExistsValidation;
//
//    @BeforeAll
//    static void setupAll() {
//        invalidCode = "";
//        validCode = EmailCodeConfirmationFactory.createCode();
//        tokenDoesNotExistsValidation = new TokenDoesNotExistsValidation();
//    }
//
//    @BeforeEach
//    void setup() {
//        Mockito.when(emailCodeConfirmationRepository.findByTokenOrThrowNotExists(invalidCode)).thenThrow(ONBEntityNotFoundException.class);
//    }
//
//    @Test
//    void validateShouldDoNothingWhenCodeIsValid() {
//        Assertions.assertDoesNotThrow(() -> {
//            tokenDoesNotExistsValidation.validate(new UserActiveAccountArgs(validCode.getCode(), this.emailCodeConfirmationRepository));
//        }, "Expected nothing exception, but throw exception");
//    }
//
//    @Nested
//    @DisplayName("TokenValidation Class Fail Test")
//    public class TokenDoesNotExistsValidationFailTest {
//        @Test
//        void validateShouldThrowONBEntityNotFoundExceptionWhenCodeIsInvalid() {
//            Assertions.assertThrows(ONBEntityNotFoundException.class, () -> {
//                tokenDoesNotExistsValidation.validate(new UserActiveAccountArgs(invalidCode, emailCodeConfirmationRepository));
//            });
//        }
//    }
//}