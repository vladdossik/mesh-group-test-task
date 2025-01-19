package mesh_group.test.service;

import mesh_group.test.dto.AccountResponseDto;
import mesh_group.test.dto.TransferRequestDto;
import mesh_group.test.model.AccountEntity;

import java.util.List;


public interface AccountService {
    AccountResponseDto getAccountByUserId(Long userId);

    void transferMoney(TransferRequestDto dto);

    List<AccountEntity> findAll();

    void saveAll(List<AccountEntity> accounts);
}