package com.github.zhuyb0614.auth.spi;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/6/16 7:49 下午
 */
public interface TokenDecipher {
    /**
     * 将token交由自定义处理
     * @param bearerToken
     */
    Object decryptToken(String bearerToken);
}
