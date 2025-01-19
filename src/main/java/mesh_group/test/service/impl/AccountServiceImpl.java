package mesh_group.test.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mesh_group.test.dto.AccountResponseDto;
import mesh_group.test.dto.TransferRequestDto;
import mesh_group.test.exception.InsufficientFundsException;
import mesh_group.test.exception.NotFoundException;
import mesh_group.test.mapper.AccountMapper;
import mesh_group.test.model.AccountEntity;
import mesh_group.test.repository.AccountRepository;
import mesh_group.test.service.AccountService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "accounts", key = "#userId")
    public AccountResponseDto getAccountByUserId(Long userId) {
        AccountEntity account = findOrThrow(userId);
        log.info("Found account for user with id={}", userId);
        return mapper.accountToAccountResponseDto(account);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#dto.fromId"),
            @CacheEvict(value = "accounts", key = "#dto.toId")
    })
    public void transferMoney(TransferRequestDto dto) {

        AccountEntity fromAccount = findOrThrow(dto.getFromId());
        AccountEntity toAccount = findOrThrow(dto.getToId());

        if (fromAccount.getBalance().compareTo(dto.getAmount()) < 0) {
            log.error("Not enough money for transfer for user with id={}", dto.getFromId());
            throw new InsufficientFundsException("User with id=" + dto.getFromId() + ", does not have enough money");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(dto.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(dto.getAmount()));

        accountRepository.saveAll(List.of(fromAccount, toAccount));

        log.info("Transaction between user with id={} and user with id={} was successful", dto.getFromId(), dto.getToId());
    }

    @Override
    public List<AccountEntity> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void saveAll(List<AccountEntity> accounts) {
        accountRepository.saveAll(accounts);
    }

    private AccountEntity findOrThrow(Long id) {
        return accountRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("Account for user with id=" + id + " was not found"));
    }
}