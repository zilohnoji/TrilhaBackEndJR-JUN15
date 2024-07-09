package factory;

import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.RoleName;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserFactory {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "User Name";
    private static final String EMAIL = "user@gmail.com";
    private static final String PASSWORD = "12345678Aa.";
    private static final RoleName ROLE_USER = RoleName.ROLE_USER;
    private static final LocalDateTime EXPIRED_AT = LocalDateTime.now().plusMinutes(30);

    public static User createUser() {
        return User.UserBuilder.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .role(ROLE_USER)
                .code("UltraCodeSecret", EXPIRED_AT)
                .build();
    }
}