package com.example.accountsystem.service;

import com.example.accountsystem.domain.Account;
import com.example.accountsystem.domain.AccountUser;
import com.example.accountsystem.dto.AccountDto;
import com.example.accountsystem.exception.AccountException;
import com.example.accountsystem.repository.AccountRepository;
import com.example.accountsystem.repository.AccountUserRepository;
import com.example.accountsystem.type.AccountStatus;
import com.example.accountsystem.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.accountsystem.type.AccountStatus.IN_USE;
import static com.example.accountsystem.type.AccountStatus.UNREGISTERED;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;

    /**
     * 사용자가 있는지 조회
     * 계좌의 번호를 생성하고
     * 계좌를 저장하고, 그 정보를 넘긴다.
     */
    @Transactional
    public AccountDto createAccount(Long userId, Long initialBalance) {
        AccountUser accountUser = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));// 해당 사용자가 없으면 예외 발생

        validateCreateAccount(accountUser);

        String newAccountNumber = accountRepository.findFirstByOrderByIdDesc() // 가장 최근에 생성된 account를 가져온다.
                .map(account -> (Integer.parseInt(account.getAccountNumber())) + 1 + "") // 문자열으로 변환
                .orElse("1000000000"); // account가 하나도 없는 경우(처음 생성)

        return AccountDto.fromEntity(
                accountRepository.save(Account.builder()
                        .accountUser(accountUser)
                        .accountNumber(newAccountNumber)
                        .accountStatus(IN_USE)
                        .balance(initialBalance)
                        .registeredAt(LocalDateTime.now())
                        .build())
        );
    }

    private void validateCreateAccount(AccountUser accountUser) {
        if (accountRepository.countByAccountUser(accountUser) >= 10) {
            throw new AccountException(ErrorCode.MAX_ACCOUNT_PER_USER_10);
        }
    }

    @Transactional
    public Account getAccount(Long id) {
        if (id < 0) {
            throw new RuntimeException("Minus");
        }
        return accountRepository.findById(id).get();
    }

    @Transactional
    public AccountDto deleteAccount(Long userId, String accountNumber) {
        AccountUser accountUser = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        validateDeleteAccount(accountUser, account);

        account.setAccountStatus(UNREGISTERED);
        account.setUnRegisteredAt(LocalDateTime.now());

        accountRepository.save(account); // for test <- 원래는 없어도되는 코드

        return AccountDto.fromEntity(account);
    }

    private void validateDeleteAccount(AccountUser accountUser, Account account) {
        if (!Objects.equals(accountUser.getId(), account.getAccountUser().getId())) {
            throw new AccountException(ErrorCode.USER_ACCOUNT_UN_MATCH);
        }

        if (account.getAccountStatus() == AccountStatus.UNREGISTERED) {
            throw new AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED);
        }

        if (account.getBalance() > 0) {
            throw new AccountException(ErrorCode.BALANCE_NOT_EMPTY);
        }
    }
}
