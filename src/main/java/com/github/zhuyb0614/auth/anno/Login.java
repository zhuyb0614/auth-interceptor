package com.github.zhuyb0614.auth.anno;

import java.lang.annotation.*;

/**
 * 登录效验
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017/9/23 14:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
    /**
     * 是否可选(登录和未登录均能访问)
     *
     * @return
     */
    boolean optional() default false;

}
