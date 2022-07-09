package com.github.zhuyb0614.auth.spi.impl;

import com.github.zhuyb0614.auth.AuthProperties;
import com.github.zhuyb0614.auth.spi.AuthJudgeNode;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/16 6:40 下午
 */
public class UriPrefixJudge extends AuthJudgeNode {
    private AuthProperties authProperties;
    private String authUriPrefix;
    private String authOptionalUriPrefix;

    public UriPrefixJudge(AuthProperties authProperties) {
        this.authProperties = authProperties;
        this.authUriPrefix = authProperties.getAuthUriPrefix() + AuthProperties.URL_SEPARATOR;
        this.authOptionalUriPrefix = authProperties.getAuthOptionalUriPrefix() + AuthProperties.URL_SEPARATOR;
    }

    @Override
    public Optional<Boolean> judge(HttpServletRequest httpServletRequest, Object handler) {
        String requestUri = httpServletRequest.getRequestURI();
        if (authProperties.getAuthUriPrefix() == null) {
            throw new IllegalStateException("enable uri prefix judge auth-uri-prefix not be null");
        }
        if (requestUri.startsWith(authUriPrefix)) {
            return Optional.of(Boolean.FALSE);
        }
        if (requestUri.startsWith(authOptionalUriPrefix)) {
            return Optional.of(Boolean.TRUE);
        }
        return Optional.empty();
    }
}
