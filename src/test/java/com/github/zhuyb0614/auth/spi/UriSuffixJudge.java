package com.github.zhuyb0614.auth.spi;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/18 4:34 下午
 */
@Component
public class UriSuffixJudge extends AuthJudgeNode {
    @Override
    public Optional<Boolean> judge(HttpServletRequest httpServletRequest, Object handler) {
        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.endsWith("/auth")) {
            return Optional.of(Boolean.FALSE);
        }
        if (requestURI.endsWith("/auth-opt")) {
            return Optional.of(Boolean.TRUE);
        }
        return Optional.empty();
    }

}
