package com.example.accountsystem.repository;

import com.example.accountsystem.domain.Account;
import com.example.accountsystem.domain.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findFirstByOrderByIdDesc(); // id를 내림차순으로 정렬한 후 첫번째 값을 가져온다.

    Integer countByAccountUser(AccountUser accountUser);
}
