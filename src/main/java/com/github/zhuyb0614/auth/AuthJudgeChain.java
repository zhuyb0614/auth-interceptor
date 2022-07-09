package com.github.zhuyb0614.auth;

import com.github.zhuyb0614.auth.spi.AuthJudgeNode;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/17 5:00 下午
 */
public class AuthJudgeChain {
    private AuthJudgeNode first;
    private AuthJudgeNode last;

    public synchronized void addNode(AuthJudgeNode node) {
        if (first == null) {
            first = node;
        } else {
            last.setNext(node);
        }
        last = node;
    }

    public Optional<Boolean> doChain(HttpServletRequest httpServletRequest, Object handler) {
        return first == null ? Optional.empty() : first.doChain(httpServletRequest, handler);
    }

}
