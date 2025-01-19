package mesh_group.test.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mesh_group.test.dto.UserResponseDto;
import mesh_group.test.dto.UserUpdateDto;
import mesh_group.test.exception.InvalidInputException;
import mesh_group.test.exception.MethodNotAllowedException;
import mesh_group.test.exception.NotFoundException;
import mesh_group.test.mapper.UserMapper;
import mesh_group.test.model.EmailDataEntity;
import mesh_group.test.model.PhoneDataEntity;
import mesh_group.test.model.UserEntity;
import mesh_group.test.repository.EmailDataRepository;
import mesh_group.test.repository.PhoneDataRepository;
import mesh_group.test.repository.UserRepository;
import mesh_group.test.service.UserService;
import mesh_group.test.specification.UserSpecifications;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailDataRepository emailDataRepository;
    private final PhoneDataRepository phoneDataRepository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", key = "#userId")
    public UserResponseDto updateUser(Long userId, UserUpdateDto userDto) {
        log.info("Start updating user with id={}", userId);
        UserEntity user = findUserOrThrow(userId);

        if (!CollectionUtils.isEmpty(userDto.getEmails())) {
            updateEmails(user, userDto.getEmails());
        }
        if (!CollectionUtils.isEmpty(userDto.getPhones())) {
            updatePhones(user, userDto.getPhones());
        }

        UserEntity updated = userRepository.save(user);
        log.info("Successfully updated user with id={}", userId);
        return mapper.userToUserResponseDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "users", key = "#userId")
    public UserResponseDto getUserById(Long userId) {
        return findUser(userId)
                .map(mapper::userToUserResponseDto)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUsersFiltered(String name, String email,
                                                  String phone, String dateOfBirth,
                                                  int page, int size) {

        Specification<UserEntity> spec = Specification
                .where(UserSpecifications.hasName(name))
                .and(UserSpecifications.hasEmail(email))
                .and(UserSpecifications.hasPhone(phone));

        if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
            LocalDate dob = LocalDate.parse(dateOfBirth);
            spec = spec.and(UserSpecifications.hasDateOfBirthAfter(dob));
        }

        Page<UserEntity> usersPage = userRepository.findAll(spec, PageRequest.of(page, size));

        List<UserResponseDto> dtoList = usersPage.stream()
                .map(mapper::userToUserResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, usersPage.getPageable(), usersPage.getTotalElements());
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", key = "#userId")
    public void deleteUser(Long userId) {
        log.info("Start deleting user with id={}", userId);
        findUser(userId)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> {
                            throw new NotFoundException("User with id=" + userId + " not found");
                        });
    }

    @Override
    public UserEntity findUserByEmailOrPhone(String login) {
        Optional<UserEntity> user;
        if (login.contains("@")) {
            user = userRepository.findByEmailsEmail(login);
        } else {
            user = userRepository.findByPhonesPhone(login);
        }
        return user.orElseThrow(() -> new NotFoundException("User with login=" + login + " not found"));
    }

    @Override
    @Transactional
    public void checkIfLockedAndLockOrThrow(List<Long> userIds) {
        if (userRepository.checkIfLocked(userIds)) {
            log.error("Some or all users with ids={} are blocked", userIds);
            throw new MethodNotAllowedException("Some or all users are blocked");
        } else {
            userRepository.updateIsLockedByIdIn(userIds, true);
        }
    }

    @Override
    @Transactional
    public void unlockUsers(List<Long> userIds) {
        userRepository.updateIsLockedByIdIn(userIds, false);
        log.info("Successfully unlocked users with ids={}", userIds);
    }

    private Optional<UserEntity> findUser(Long userId) {
        return userRepository.findById(userId);
    }

    private UserEntity findUserOrThrow(Long userId) {
        return findUser(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
    }

    private void updateEmails(UserEntity user, Set<String> emails) {
        List<EmailDataEntity> existingEmails = emailDataRepository.findByEmailIn(emails);
        existingEmails.forEach(e -> {
            if (!e.getUser().getId().equals(user.getId())) {
                throw new InvalidInputException("Email " + e.getEmail() + " is already in use");
            }
        });

        Set<EmailDataEntity> newEmails = processEmails(emails);
        user.addEmails(newEmails);
    }

    private Set<EmailDataEntity> processEmails(Set<String> emails) {
        return emails.stream().map(newEmail -> {
            EmailDataEntity emailData = new EmailDataEntity();
            emailData.setEmail(newEmail);
            return emailData;
        }).collect(Collectors.toSet());
    }

    private void updatePhones(UserEntity user, Set<String> phones) {
        List<PhoneDataEntity> existingPhones = phoneDataRepository.findByPhoneIn(phones);
        existingPhones.forEach(p -> {
            if (!p.getUser().getId().equals(user.getId())) {
                throw new InvalidInputException("Phone " + p.getPhone() + " is already in use");
            }
        });

        Set<PhoneDataEntity> newPhones = processPhones(phones);
        user.addPhones(newPhones);
    }

    private Set<PhoneDataEntity> processPhones(Set<String> phones) {
        return phones.stream().map(newPhone -> {
            PhoneDataEntity phoneData = new PhoneDataEntity();
            phoneData.setPhone(newPhone);
            return phoneData;
        }).collect(Collectors.toSet());
    }
}
