package com.example.accountsystem.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;
}
