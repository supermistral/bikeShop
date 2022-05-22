package com.supershaun.bikeshop.exceptions;

import com.supershaun.bikeshop.responses.Messages;

import javax.persistence.EntityNotFoundException;

public class ItemNotFoundException extends EntityNotFoundException {
    public ItemNotFoundException() {
        super(Messages.ItemIdNotFound.toString());
    }
}
