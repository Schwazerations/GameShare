package com.game.common.exception.user;

public class RegisterUsernameNotUniqueException extends UserException {
    public RegisterUsernameNotUniqueException() {
        super("user.register.utel.duplicate", null);
    }
}
