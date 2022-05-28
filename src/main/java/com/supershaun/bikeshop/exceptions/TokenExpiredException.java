package com.supershaun.bikeshop.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException() {
        super("Token was expired");
    }
}
