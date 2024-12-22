package com.game.common.exception.user;

import com.game.common.exception.BaseException;

public class UserException extends BaseException {
    public UserException(String code, String defaultMessage) {
        super("user", code, defaultMessage);
    }
}
