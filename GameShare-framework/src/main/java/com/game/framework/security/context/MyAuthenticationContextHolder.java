package com.game.framework.security.context;

import org.springframework.security.core.Authentication;

/**
 * 身份验证信息
 *
 */
public class MyAuthenticationContextHolder
{
    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    public static Authentication getContext()
    {
        return contextHolder.get();
    }

    public static String getUname() {
        return getContext().getName();
    }

    public static String getUpsw() {
        return getContext().getCredentials().toString();
    }

    public static void setContext(Authentication context)
    {
        contextHolder.set(context);
    }

    public static void clearContext()
    {
        contextHolder.remove();
    }
}
