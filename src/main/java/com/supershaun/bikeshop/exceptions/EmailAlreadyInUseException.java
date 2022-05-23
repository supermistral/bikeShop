package com.supershaun.bikeshop.exceptions;

import com.supershaun.bikeshop.responses.Messages;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super(Messages.EmailAlreadyInUse.toString());
    }
}
