package com.github.zhuyb0614.auth;

import com.github.zhuyb0614.auth.spi.BaseAuthJudgeNode;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/17 5:00 下午
 */
public class AuthJudgeChain {
    private BaseAuthJudgeNode first;
    private BaseAuthJudgeNode last;

    public synchronized void addNode(BaseAuthJudgeNode node) {
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
