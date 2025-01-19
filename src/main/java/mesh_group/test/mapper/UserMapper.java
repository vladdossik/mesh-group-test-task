package mesh_group.test.mapper;

import mesh_group.test.dto.UserResponseDto;
import mesh_group.test.model.EmailDataEntity;
import mesh_group.test.model.PhoneDataEntity;
import mesh_group.test.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "emails", source = "emails", qualifiedByName = "mapEmails")
    @Mapping(target = "phones", source = "phones", qualifiedByName = "mapPhones")
    UserResponseDto userToUserResponseDto(UserEntity user);

    @Named("mapEmails")
    default Set<String> mapEmails(Set<EmailDataEntity> emails) {
        return emails.stream()
                .map(EmailDataEntity::getEmail)
                .collect(Collectors.toSet());
    }

    @Named("mapPhones")
    default Set<String> mapPhones(Set<PhoneDataEntity> phones) {
        return phones.stream()
                .map(PhoneDataEntity::getPhone)
                .collect(Collectors.toSet());
    }
}
