package com.heartape.live.ums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    ACTIVE(1),
    LOCKED(2),
    DELETED(3);

    private final int value;

}
