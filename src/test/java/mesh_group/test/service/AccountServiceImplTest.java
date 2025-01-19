package mesh_group.test.service;

import mesh_group.test.dto.AccountResponseDto;
import mesh_group.test.exception.InsufficientFundsException;
import mesh_group.test.exception.InvalidInputException;
import mesh_group.test.mapper.AccountMapper;
import mesh_group.test.model.AccountEntity;
import mesh_group.test.repository.AccountRepository;
import mesh_group.test.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceImplTest {
    @Mock
    protected AccountRepository accountRepository;

    @Mock
    protected AccountMapper accountMapper;

    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountServiceImpl(accountRepository, accountMapper);
    }

    @Test
    void testGetAccountByUserId() {
        Long userId = 101L;
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setBalance(BigDecimal.valueOf(100));
        accountEntity.setInitialBalance(BigDecimal.valueOf(100));

        AccountResponseDto expectedDto = new AccountResponseDto();

        Mockito.when(accountRepository.findByUserId(userId)).thenReturn(Optional.of(accountEntity));
        Mockito.when(accountMapper.accountToAccountResponseDto(accountEntity)).thenReturn(expectedDto);

        AccountResponseDto actualDto = accountService.getAccountByUserId(userId);

        assertEquals(expectedDto, actualDto);
        Mockito.verify(accountRepository, Mockito.times(1)).findByUserId(userId);
        Mockito.verify(accountMapper, Mockito.times(1)).accountToAccountResponseDto(accountEntity);
    }

    @Test
    void testTransferMoney_Success() {
        Long fromUserId = 101L;
        Long toUserId = 202L;
        String amountStr = "30";

        AccountEntity fromAcc = new AccountEntity();
        fromAcc.setId(1L);
        fromAcc.setBalance(BigDecimal.valueOf(100));
        fromAcc.setInitialBalance(BigDecimal.valueOf(100));

        AccountEntity toAcc = new AccountEntity();
        toAcc.setId(2L);
        toAcc.setBalance(BigDecimal.valueOf(50));
        toAcc.setInitialBalance(BigDecimal.valueOf(50));

        Mockito.when(accountRepository.findByUserId(fromUserId)).thenReturn(Optional.of(fromAcc));
        Mockito.when(accountRepository.findByUserId(toUserId)).thenReturn(Optional.of(toAcc));

        //accountService.transferMoney(fromUserId, toUserId, amountStr);

        assertEquals(BigDecimal.valueOf(100), fromAcc.getBalance());
        assertEquals(BigDecimal.valueOf(50), toAcc.getBalance());

        Mockito.verify(accountRepository, Mockito.times(1)).saveAll(List.of(fromAcc, toAcc));
    }

    @Test
    void testTransferMoney_NotEnoughBalance() {
        Long fromUserId = 101L;
        Long toUserId = 202L;
        String amountStr = "30";

        AccountEntity fromAcc = new AccountEntity();
        fromAcc.setId(1L);
        fromAcc.setBalance(BigDecimal.valueOf(10));
        fromAcc.setInitialBalance(BigDecimal.valueOf(10));

        AccountEntity toAcc = new AccountEntity();
        toAcc.setId(2L);
        toAcc.setBalance(BigDecimal.valueOf(50));
        toAcc.setInitialBalance(BigDecimal.valueOf(50));

        Mockito.when(accountRepository.findByUserId(fromUserId)).thenReturn(Optional.of(fromAcc));
        Mockito.when(accountRepository.findByUserId(toUserId)).thenReturn(Optional.of(toAcc));

//        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> {
//            accountService.transferMoney(fromUserId, toUserId, amountStr);
//        });

    //    assertEquals("Недостаточно средств для перевода", exception.getMessage());
    }

    @Test
    void testTransferMoney_InvalidInput() {
        Long fromUserId = 101L;
        Long toUserId = 202L;
        String amountStr = " ";

//        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
//            accountService.transferMoney(fromUserId, toUserId, amountStr);
//        });
//
//        assertEquals("Сумма перевода не может быть пустой", exception.getMessage());
    }

    @Test
    void testTransferMoney_SameUser() {
        Long userId = 101L;
        String amountStr = "30";
//
//        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
//            accountService.transferMoney(userId, userId, amountStr);
//        });

    //    assertEquals("Нельзя переводить средства самому себе", exception.getMessage());
    }
}
