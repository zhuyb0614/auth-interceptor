package com.github.zhuyb0614.auth.controller;

import com.github.zhuyb0614.auth.mock.UserId;
import com.github.zhuyb0614.auth.anno.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/18 2:37 下午
 */
@RestController
public class TestController {

    public static final String AUTH_PING_AUTH = "/auth/ping/auth";
    public static final String AUTH_OPT_PING_AUTH_OPT = "/auth-opt/ping/auth-opt";
    public static final String PING = "/ping";

    @GetMapping(AUTH_PING_AUTH)
    @Login
    public ResponseEntity<String> pingLogin(@ApiIgnore UserId userId) {
        return ResponseEntity.ok("pong:" + userId.getUserId());
    }

    @GetMapping(AUTH_OPT_PING_AUTH_OPT)
    @Login(optional = true)
    public ResponseEntity<String> pingLoginOptional(@ApiIgnore UserId userId) {
        return ResponseEntity.ok("pong:" + (userId == null ? "" : userId.getUserId()));
    }

    @GetMapping(PING)
    public ResponseEntity<String> ping(@ApiIgnore UserId userId) {
        return ResponseEntity.ok("pong:" + (userId == null ? "" : userId.getUserId()));
    }
}
