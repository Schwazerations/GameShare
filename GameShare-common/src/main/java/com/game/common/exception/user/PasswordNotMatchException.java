package com.game.common.exception.user;

public class PasswordNotMatchException extends UserException {
    public PasswordNotMatchException() {
        super("user.password.not.match", null);
    }
}
