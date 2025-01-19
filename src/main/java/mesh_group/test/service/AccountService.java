package mesh_group.test.service;

import mesh_group.test.dto.AccountResponseDto;
import mesh_group.test.dto.TransferRequestDto;


public interface AccountService {
    AccountResponseDto getAccountByUserId(Long userId);

    void transferMoney(TransferRequestDto dto);
}