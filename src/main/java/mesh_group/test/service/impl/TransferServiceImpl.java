package mesh_group.test.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mesh_group.test.dto.TransferRequestDto;
import mesh_group.test.service.AccountService;
import mesh_group.test.service.TransferService;
import mesh_group.test.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {
    private final AccountService accountService;
    private final UserService userService;

    @Override
    public void transfer(TransferRequestDto dto) {
        try {
            List<Long> userIds = List.of(dto.getFromId(), dto.getToId());
            userService.checkIfLockedAndLockOrThrow(userIds);
            log.info("Successfully locked users with ids={}", userIds);
            accountService.transferMoney(dto);
            log.info("Successfully transferred money for users with ids={}", userIds);
        } catch (Exception e) {
            userService.unlockUsers(List.of(dto.getFromId(), dto.getToId()));
        }
    }
}
