package com.game.common.exception.user;

public class BadUsernamePasswordException extends UserException{
    public BadUsernamePasswordException() {
        super("user.username.password.invalid", null);
    }
}
