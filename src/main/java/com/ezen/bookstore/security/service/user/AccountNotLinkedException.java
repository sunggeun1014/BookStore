package com.ezen.bookstore.security.service.user;

import org.springframework.security.core.AuthenticationException;

public class AccountNotLinkedException extends AuthenticationException {

    public AccountNotLinkedException(String message) {
        super(message);
    }

}