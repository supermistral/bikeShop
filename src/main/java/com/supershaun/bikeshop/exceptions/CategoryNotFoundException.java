package com.supershaun.bikeshop.exceptions;

import com.supershaun.bikeshop.responses.Messages;

import javax.persistence.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException() {
        super(Messages.CategoryIdNotFound.toString());
    }
}
