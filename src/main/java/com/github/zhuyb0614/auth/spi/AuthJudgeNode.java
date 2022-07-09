package com.github.zhuyb0614.auth.spi;

import com.github.zhuyb0614.auth.spi.impl.AnnotationJudge;
import com.github.zhuyb0614.auth.spi.impl.UriPrefixJudge;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/17 5:08 下午
 */
public abstract class AuthJudgeNode {
    protected AuthJudgeNode next;

    /**
     * 是否可选登录,多个实现共同处理,只要一个被校验到即可生效
     * 默认提供以下几种方式,处理顺序相同
     *
     * @param httpServletRequest
     * @param handler
     * @return true = 必须要登录 ,  false = 可以登录也可以不登录 , empty = 不需要登录
     * @see AnnotationJudge 基于注解校验
     * @see UriPrefixJudge 基于URI前缀
     * <p>
     * Boolean isOptionalLogin = null;
     * for (LoginValid valid : loginValid) {
     * isOptionalLogin = valid.isOptionalLogin(request, handler);
     * if (isOptionalLogin != null) {
     * break;
     * }
     * }
     * if (isOptionalLogin == null) {
     * return true;
     * }
     */
    public Optional<Boolean> doChain(HttpServletRequest httpServletRequest, Object handler) {
        Optional<Boolean> booleanOptional = judge(httpServletRequest, handler);
        if (booleanOptional.isPresent()) {
            return booleanOptional;
        } else {
            return doNextOrEmpty(httpServletRequest, handler);
        }
    }

    /**
     * 设置下一个判断器
     *
     * @param next
     */
    public void setNext(AuthJudgeNode next) {
        this.next = next;
    }

    private Optional<Boolean> doNextOrEmpty(HttpServletRequest httpServletRequest, Object handler) {
        return next == null ? Optional.empty() : next.judge(httpServletRequest, handler);
    }

    /**
     * 子类实现判读逻辑
     *
     * @param httpServletRequest
     * @param handler
     * @return true = 必须要登录 ,  false = 可以登录也可以不登录 , empty = 无法判断
     */
    protected abstract Optional<Boolean> judge(HttpServletRequest httpServletRequest, Object handler);

}
