package com.donatoordep.rg.code.util;


import com.donatoordep.rg.code.repositories.impl.EmailCodeConfirmationRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ApplicationConfigValidationTest {

    @Mock
    protected EmailCodeConfirmationRepository emailCodeConfirmationRepository;
}
