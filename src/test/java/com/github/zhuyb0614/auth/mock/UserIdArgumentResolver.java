package com.github.zhuyb0614.auth.mock;

import com.github.zhuyb0614.auth.Constants;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/6/16 8:17 下午
 */
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserId.class.isAssignableFrom(parameter.getParameter().getType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object nativeRequest = webRequest.getNativeRequest();
        if (nativeRequest != null && nativeRequest instanceof HttpServletRequest) {
            return ((HttpServletRequest) nativeRequest).getAttribute(Constants.USER_ID_ATTRIBUTE_NAME);
        }
        return null;
    }
}
