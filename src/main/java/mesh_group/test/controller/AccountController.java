package mesh_group.test.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mesh_group.test.dto.AccountResponseDto;
import mesh_group.test.dto.TransferRequestDto;
import mesh_group.test.service.AccountService;
import mesh_group.test.service.TransferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransferService transferService;

    @GetMapping("/user/{userId}")
    public AccountResponseDto getAccountByUserId(@PathVariable Long userId) {
        return accountService.getAccountByUserId(userId);
    }

    @PostMapping("/transfer")
    public void transferMoney(@RequestBody @Valid TransferRequestDto dto) {
        transferService.transfer(dto);
    }
}