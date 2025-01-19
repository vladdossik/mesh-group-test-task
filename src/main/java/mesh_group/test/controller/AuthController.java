package mesh_group.test.controller;

import lombok.RequiredArgsConstructor;
import mesh_group.test.dto.LoginRequestDto;
import mesh_group.test.dto.LoginResponseDto;
import mesh_group.test.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto request) {
        return authService.login(request);
    }
}