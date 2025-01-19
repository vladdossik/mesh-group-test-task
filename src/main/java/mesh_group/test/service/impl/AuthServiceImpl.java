package mesh_group.test.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mesh_group.test.dto.LoginRequestDto;
import mesh_group.test.dto.LoginResponseDto;
import mesh_group.test.exception.InvalidInputException;
import mesh_group.test.model.UserEntity;
import mesh_group.test.security.JwtTokenProvider;
import mesh_group.test.service.AuthService;
import mesh_group.test.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    //private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        String emailOrPhone = requestDto.getEmailOrPhone();
        log.info("Attempting to login for {}", emailOrPhone);
        UserEntity user = findUserByEmailOrPhone(emailOrPhone);
        if (!isPasswordValid(requestDto.getPassword(), user.getPassword())) {
            log.error("Incorrect password for {}", emailOrPhone);
            throw new InvalidInputException("Incorrect password for " + emailOrPhone);
        }

        log.info("Successfully logged in for {}", user.getId());
        return new LoginResponseDto(jwtTokenProvider.generateToken(user.getId()));
    }

    private UserEntity findUserByEmailOrPhone(String login) {
        return userService.findUserByEmailOrPhone(login);
    }

    private Boolean isPasswordValid(String requestPass, String dbPass) {
        return requestPass.equals(dbPass);
        //идеальная реализация должна использовать шифрование паролей
        //return passwordEncoder.matches(requestPass, dbPass);
    }
}