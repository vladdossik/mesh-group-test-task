package mesh_group.test.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mesh_group.test.model.AccountEntity;
import mesh_group.test.repository.AccountRepository;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceScheduler {
    private static final BigDecimal MULTIPLIER_FOR_TWO_HUNDRED_SEVEN_PERCENT = new BigDecimal("2.07");
    private static final BigDecimal MULTIPLIER_FOR_TEN_PERCENT = new BigDecimal("1.1");

    private final AccountRepository accountRepository;

    @Scheduled(fixedRateString = "${scheduler.balance.fixedRate}")
    @SchedulerLock(name = "increaseBalancesLock")
    public void increaseBalances() {
        BigDecimal currentBalance;
        BigDecimal maxPossibleBalance;
        BigDecimal newBalance;
        List<AccountEntity> accounts = accountRepository.findAll();
        for (AccountEntity account : accounts) {
            currentBalance = account.getBalance();
            maxPossibleBalance = account.getInitialBalance().multiply(MULTIPLIER_FOR_TWO_HUNDRED_SEVEN_PERCENT);
            newBalance = currentBalance.multiply(MULTIPLIER_FOR_TEN_PERCENT);
            if (canIncreaseBalance(newBalance, maxPossibleBalance)) {
                account.setBalance(newBalance);
            }
        }
        accountRepository.saveAll(accounts);
        log.debug("Succesfully updated balances for {} accounts", accounts.size());
    }

    private boolean canIncreaseBalance(BigDecimal newBalance, BigDecimal maxPossibleBalance) {
        return maxPossibleBalance.compareTo(newBalance) >= 0;
    }
}