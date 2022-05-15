package com.supershaun.bikeshop.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DefaultMessageEntity {
    private Messages message;

    public String getMessage() {
        return message.toString();
    }
}
