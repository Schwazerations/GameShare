package com.game.common.exception.user;

import java.io.Serial;

public class CaptchaException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super("user.captcha.invalid", null);
    }
}
