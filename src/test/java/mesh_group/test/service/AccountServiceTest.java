package mesh_group.test.service;

import mesh_group.test.AbstractInitialization;
import mesh_group.test.exception.InsufficientFundsException;
import mesh_group.test.exception.NotFoundException;
import mesh_group.test.mapper.AccountMapper;
import mesh_group.test.repository.AccountRepository;
import mesh_group.test.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class AccountServiceTest extends AbstractInitialization {

    @Mock
    protected AccountRepository accountRepository;

    @Mock
    protected AccountMapper accountMapper;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountServiceImpl(accountRepository, accountMapper);
    }

    @Test
    void shouldFailWhenAccountNotFound() {
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.transferMoney(transferRequestDto));
    }

    @Test
    void shouldFailWhenBalanceIsLessThanRequestAmount() {
        transferRequestDto.setAmount(BigDecimal.valueOf(1000));

        when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserId(2L)).thenReturn(Optional.of(toAccount));

        assertThrows(InsufficientFundsException.class, () -> accountService.transferMoney(transferRequestDto));
    }

    @Test
    void shouldSuccessfullyTransferMoney() {
        transferRequestDto.setAmount(BigDecimal.valueOf(30));
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserId(2L)).thenReturn(Optional.of(toAccount));

        accountService.transferMoney(transferRequestDto);

        Assertions.assertEquals(fromAccount.getBalance(), BigDecimal.valueOf(80));
        Assertions.assertEquals(toAccount.getBalance(), BigDecimal.valueOf(150));
    }
}
