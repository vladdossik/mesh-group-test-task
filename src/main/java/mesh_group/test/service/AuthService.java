package mesh_group.test.service;

import mesh_group.test.dto.LoginRequestDto;
import mesh_group.test.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);
}