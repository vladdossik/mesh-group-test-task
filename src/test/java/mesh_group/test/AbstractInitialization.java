package mesh_group.test;


import mesh_group.test.dto.LoginRequestDto;
import mesh_group.test.dto.TransferRequestDto;
import mesh_group.test.model.AccountEntity;
import mesh_group.test.model.EmailDataEntity;
import mesh_group.test.model.PhoneDataEntity;
import mesh_group.test.model.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AbstractInitialization {
    protected static UserEntity defaultUser;
    protected static AccountEntity fromAccount;
    protected static AccountEntity toAccount;
    protected static TransferRequestDto transferRequestDto;
    protected static List<Long> userIdsList;
    protected static UserEntity anotherUser = new UserEntity();
    protected static EmailDataEntity emailData;
    protected static PhoneDataEntity phoneData;
    protected static LoginRequestDto loginRequest;


    @BeforeAll
    public static void init() {
        defaultUser = new UserEntity();
        fromAccount = new AccountEntity(1L, defaultUser, BigDecimal.valueOf(100), BigDecimal.valueOf(110));
        toAccount = new AccountEntity(2L, defaultUser, BigDecimal.valueOf(110), BigDecimal.valueOf(120));
        transferRequestDto = new TransferRequestDto(1L, 2L, BigDecimal.valueOf(30));
        userIdsList = List.of(1L, 2L);
        anotherUser = new UserEntity();
        anotherUser.setName("anotheruser");
        anotherUser.setPassword("$2a$10$KIXeCmjaH8eRQGVKzOqPzOtP9SWsZT/7n3vAtTP5Vv9WRcTp3Dp2y");

        emailData = new EmailDataEntity();
        emailData.setEmail("user@example.com");
        emailData.setUser(anotherUser);

        anotherUser.setEmails(Set.of(emailData));

        phoneData = new PhoneDataEntity();
        phoneData.setPhone("1234567890");
        phoneData.setUser(anotherUser);

        anotherUser.setPhones(Set.of(phoneData));

        loginRequest = new LoginRequestDto();
        loginRequest.setEmailOrPhone("user@example.com");
        loginRequest.setPassword("\\$2$10$KIXeCmjaH8eRQGVKzOqPzOtP9SWsZT/7n3vAtTP5Vv9WRcTp3Dp2y");
    }

}
