package com.supershaun.bikeshop.exceptions;

import com.supershaun.bikeshop.responses.Messages;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends UsernameNotFoundException {
    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }
}
