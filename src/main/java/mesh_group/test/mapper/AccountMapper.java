package mesh_group.test.mapper;

import mesh_group.test.dto.AccountResponseDto;
import mesh_group.test.model.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponseDto accountToAccountResponseDto(AccountEntity account);
}
