package com.game.common.exception.user;


public class UserNotFoundException extends UserException {
    public UserNotFoundException() {
        super("user.not.found", null);
    }
}
