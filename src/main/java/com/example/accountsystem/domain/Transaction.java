package com.example.accountsystem.domain;

import com.example.accountsystem.type.TransactionResultType;
import com.example.accountsystem.type.TransactionType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // 거래 종류

    @Enumerated(EnumType.STRING)
    private TransactionResultType transactionResultType; // 거래 결과

    @ManyToOne
    private Account account; // 거래가 발생한 계좌

    private Long amount; // 거래 금액
    private Long balanceSnapshot; // 거래 후 계좌 잔액
    private String transactionId; // 거래 아이디
    private LocalDateTime transactedAt; // 거래일시

    @CreatedDate
    private LocalDateTime createdAt; // 생성일시

    @LastModifiedDate
    private LocalDateTime updatedAt; // 최종 수정일시
}
