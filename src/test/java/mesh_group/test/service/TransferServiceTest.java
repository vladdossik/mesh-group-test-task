package mesh_group.test.service;

import mesh_group.test.AbstractInitialization;

import mesh_group.test.exception.InsufficientFundsException;
import mesh_group.test.exception.MethodNotAllowedException;
import mesh_group.test.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class TransferServiceTest extends AbstractInitialization {
    @Mock
    protected AccountService accountService;

    @Mock
    protected UserService userService;

    private TransferServiceImpl transferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferService = new TransferServiceImpl(accountService, userService);
    }


    @Test
    void testTransferMoney_Success() {

        doNothing().when(userService).checkIfLockedAndLockOrThrow(userIdsList);
        doNothing().when(accountService).transferMoney(transferRequestDto);

        transferService.transfer(transferRequestDto);

        verify(userService).checkIfLockedAndLockOrThrow(userIdsList);
        verify(accountService).transferMoney(transferRequestDto);
        verify(userService).unlockUsers(userIdsList);
        verifyNoMoreInteractions(userService, accountService);
    }

    @Test
    void shouldUnlockUsersWhenCheckIfLockedFails() {
        doThrow(new MethodNotAllowedException("Users are locked"))
                .when(userService).checkIfLockedAndLockOrThrow(userIdsList);

        transferService.transfer(transferRequestDto);
        verify(userService).checkIfLockedAndLockOrThrow(userIdsList);
        verify(userService).unlockUsers(userIdsList);
    }


    @Test
    void shouldUnlockUsersWhenTransferFails() {
        doNothing().when(userService).checkIfLockedAndLockOrThrow(userIdsList);
        doThrow(new InsufficientFundsException("Insufficient funds")).when(accountService).transferMoney(transferRequestDto);

        transferService.transfer(transferRequestDto);
        verify(userService).checkIfLockedAndLockOrThrow(userIdsList);
        verify(accountService).transferMoney(transferRequestDto);
        verify(userService).unlockUsers(userIdsList);
    }

    @Test
    void shouldNotContinueIfLockFails() {
        doThrow(new RuntimeException("Failed lock"))
                .when(userService).checkIfLockedAndLockOrThrow(userIdsList);
        transferService.transfer(transferRequestDto);

        verify(userService).checkIfLockedAndLockOrThrow(userIdsList);
        verify(userService).unlockUsers(userIdsList);
        verifyNoInteractions(accountService);
    }
}
