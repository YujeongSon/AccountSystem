package com.example.accountsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInfo { // client-controller 간의 응답
    private String accountNumber;
    private Long balance;
}
