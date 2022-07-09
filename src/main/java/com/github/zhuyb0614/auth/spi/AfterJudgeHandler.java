package com.github.zhuyb0614.auth.spi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/6/29 11:55 上午
 */
public interface AfterJudgeHandler {
    /**
     * 经过登录判断链后自定义处理
     *
     * @param isOptionalLogin 是否可选登录
     * @param request
     * @param response
     */
    void handle(boolean isOptionalLogin, HttpServletRequest request, HttpServletResponse response);
}
