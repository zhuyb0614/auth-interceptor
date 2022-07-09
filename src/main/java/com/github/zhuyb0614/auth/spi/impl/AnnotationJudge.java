package com.github.zhuyb0614.auth.spi.impl;

import com.github.zhuyb0614.auth.anno.Login;
import com.github.zhuyb0614.auth.spi.AuthJudgeNode;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/16 6:36 下午
 */
public class AnnotationJudge extends AuthJudgeNode {


    @Override
    public Optional<Boolean> judge(HttpServletRequest httpServletRequest, Object handler) {
        if ((handler instanceof HandlerMethod)) {
            Login annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
            if (annotation != null) {
                return Optional.of(annotation.optional());
            }
        }
        return Optional.empty();
    }
}
