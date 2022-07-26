package com.github.zhuyb0614.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/16 6:41 下午
 */
@Data
@ConfigurationProperties(prefix = "yb.auth")
public class AuthProperties {
    public static final String URL_SEPARATOR = "/";
    /**
     * 是否开启权限校验拦截器
     */
    private boolean openSwitch = true;
    /**
     * 是否开启注解校验
     */
    private boolean enableAnnotationValid = false;
    /**
     * 是否开启URI前缀校验
     */
    private boolean enableUriPrefixValid = false;
    /**
     * 必须登录接口前缀
     */
    private String authUriPrefix;
    /**
     * 选择登录接口前缀
     */
    private String authOptionalUriPrefix = URL_SEPARATOR;


    public void setAuthOptionalUriPrefix(String authOptionalUriPrefix) {
        validPrefix(authOptionalUriPrefix);
        this.authOptionalUriPrefix = authOptionalUriPrefix;
    }

    public void setAuthUriPrefix(String authUriPrefix) {
        validPrefix(authUriPrefix);
        this.authUriPrefix = authUriPrefix;
    }

    private void validPrefix(String prefix) {
        if (!prefix.startsWith(URL_SEPARATOR) || prefix.endsWith(URL_SEPARATOR)) {
            throw new IllegalArgumentException("must start with '/' and not end with '/'");
        }
    }

}
