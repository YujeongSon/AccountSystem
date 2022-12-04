package com.example.accountsystem.domain;

import com.example.accountsystem.exception.AccountException;
import com.example.accountsystem.type.AccountStatus;
import com.example.accountsystem.type.ErrorCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account extends BaseEntity {
    @ManyToOne
    private AccountUser accountUser; // 소유자 정보
    
    private String accountNumber; // 계좌 번호

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus; // 계좌 상태
    
    private Long balance; // 계좌 잔액
    private LocalDateTime registeredAt; // 계좌 등록일시
    private LocalDateTime unRegisteredAt; // 계좌 해지일시

    public void useBalance(Long amount) {
        if (amount > balance) {
            throw new AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE);
        }

        balance -= amount;
    }

    public void cancelBalance(Long amount) {
        if (amount < 0) {
            throw new AccountException(ErrorCode.INVALID_REQUEST);
        }

        balance += amount;
    }
}
