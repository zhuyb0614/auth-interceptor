package com.github.zhuyb0614.auth.excption;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/6/16 7:47 下午
 */
public class NoLoginException extends RuntimeException {
    public NoLoginException() {
    }

    public NoLoginException(String message) {
        super(message);
    }
}
