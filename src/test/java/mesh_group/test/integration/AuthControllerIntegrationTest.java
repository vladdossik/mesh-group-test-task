package mesh_group.test.integration;

import mesh_group.test.BaseIntegrationTest;
import mesh_group.test.dto.LoginRequestDto;
import mesh_group.test.dto.LoginResponseDto;
import mesh_group.test.model.EmailDataEntity;
import mesh_group.test.model.PhoneDataEntity;
import mesh_group.test.model.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void testLogin() throws Exception {
        UserEntity user = new UserEntity();
        user.setName("testuser");
        user.setPassword("testpass");
        user.setEmails(new HashSet<>());
        user.setPhones(new HashSet<>());

        EmailDataEntity emailData = new EmailDataEntity();
        emailData.setEmail("user@example.com");
        emailData.setUser(user);
        user.getEmails().add(emailData);

        PhoneDataEntity phoneData = new PhoneDataEntity();
        phoneData.setPhone("1234567890");
        phoneData.setUser(user);
        user.getPhones().add(phoneData);

        userRepository.save(user);

        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmailOrPhone("user@example.com");
        loginRequest.setPassword("testpass");

        String requestJson = objectMapper.writeValueAsString(loginRequest);
        String responseJson = mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponseDto loginResponse = objectMapper.readValue(responseJson, LoginResponseDto.class);
        Assertions.assertNotNull(loginResponse.getToken(), "Токен не должен быть null");
        Assertions.assertFalse(loginResponse.getToken().isEmpty(), "Токен не должен быть пустым");
    }
}