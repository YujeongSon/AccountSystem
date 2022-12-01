package com.example.accountsystem.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private AccountUser accountUser; // 소유자 정보
    
    private String accountNumber; // 계좌 번호

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus; // 계좌 상태
    
    private Long balance; // 계좌 잔액
    private LocalDateTime registeredAt; // 계좌 등록일시
    private LocalDateTime unRegisteredAt; // 계좌 해지일시
    private LocalDateTime createdAt; // 생성일시
    private LocalDateTime updatedAt; // 최종 수정일시
}
