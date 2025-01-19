package mesh_group.test.service;


import mesh_group.test.dto.UserResponseDto;
import mesh_group.test.dto.UserUpdateDto;
import mesh_group.test.model.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserService {
    UserResponseDto updateUser(Long userId, UserUpdateDto userDto);

    UserResponseDto getUserById(Long userId);

    Page<UserResponseDto> getUsersFiltered(String name, String email, String phone, String dateOfBirth, int page, int size);

    void deleteUser(Long userId);

    UserEntity findUserByEmailOrPhone(String login);

    void checkIfLockedAndLockOrThrow(List<Long> userIds);

    void unlockUsers(List<Long> userIds);
}