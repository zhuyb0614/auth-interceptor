package com.github.zhuyb0614.auth.mock;

import com.github.zhuyb0614.auth.spi.TokenDecipher;
import org.springframework.stereotype.Component;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/18 3:56 下午
 */
@Component
public class ParseIntTokenDecipher implements TokenDecipher {

    @Override
    public Object decryptToken(String bearerToken) {
        UserId userId = new UserId();
        userId.setUserId(Integer.parseInt(bearerToken));
        return userId;
    }
}
