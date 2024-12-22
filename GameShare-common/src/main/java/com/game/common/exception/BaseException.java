package com.game.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 业务模块（user/file）
     */
    private String module;
    /**
     * 错误码
     */
    private String code;    // 返回给前端的
    /**
     * 错误消息
     */
    private String defaultMessage;  // 给程序员看的

}
