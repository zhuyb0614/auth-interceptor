package com.github.zhuyb0614.auth.interceptor;

import com.github.zhuyb0614.auth.AuthJudgeChain;
import com.github.zhuyb0614.auth.spi.AfterJudgeHandler;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/5 4:07 下午
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private AuthJudgeChain authJudgeChain;
    private AfterJudgeHandler afterJudgeHandler;

    public AuthInterceptor(AuthJudgeChain authJudgeChain, AfterJudgeHandler afterJudgeHandler) {
        this.authJudgeChain = authJudgeChain;
        this.afterJudgeHandler = afterJudgeHandler;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional<Boolean> isOptionalLogin = authJudgeChain.doChain(request, handler);
        if (isOptionalLogin.isPresent()) {
            afterJudgeHandler.handle(isOptionalLogin.get(), request, response);
        }
        return true;
    }

}
