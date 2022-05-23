package com.supershaun.bikeshop.exceptions;

import com.supershaun.bikeshop.responses.Messages;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super(Messages.RoleNotFoundException.toString());
    }
}
