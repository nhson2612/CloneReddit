package com.example.redditclone.excecption;

public class AccountNotVerified extends RuntimeException {
    public AccountNotVerified(String message) {
        super(message);
    }
}
