package com.example.accountsystem.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AccountUser { // 소유자 정보
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private LocalDateTime createdAt; // 생성일시
    private LocalDateTime updatedAt; // 최종 수정일시
}
