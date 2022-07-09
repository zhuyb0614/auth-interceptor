package com.github.zhuyb0614.auth.spi.impl;

import com.github.zhuyb0614.auth.excption.NoLoginException;
import com.github.zhuyb0614.auth.spi.AfterJudgeHandler;
import com.github.zhuyb0614.auth.spi.TokenDecipher;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/6/29 11:57 上午
 */
public class DefaultAfterJudgeHandler implements AfterJudgeHandler {
    public static final String USER_ID_ATTRIBUTE_NAME = "user-id";
    private TokenDecipher tokenDecipher;

    public DefaultAfterJudgeHandler(TokenDecipher tokenDecipher) {
        this.tokenDecipher = tokenDecipher;
    }

    @Override
    public void handle(boolean isOptionalLogin, HttpServletRequest request, HttpServletResponse response) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!isOptionalLogin && StringUtils.isEmpty(bearerToken)) {
            throw new NoLoginException("token不能为空");
        }
        if (!StringUtils.isEmpty(bearerToken)) {
            Object object = tokenDecipher.decryptToken(bearerToken);
            request.setAttribute(USER_ID_ATTRIBUTE_NAME, object);
        }
    }
}
