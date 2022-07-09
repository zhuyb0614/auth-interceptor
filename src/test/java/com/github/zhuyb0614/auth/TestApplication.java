package com.github.zhuyb0614.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/18 2:36 下午
 */
@SpringBootApplication
@Slf4j
public class TestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TestApplication.class, args);
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            log.info("Swagger URL http://{}:{}{}/swagger-ui.html", hostAddress, port == null ? "8080" : port, env.getProperty("server.servlet.context-path"));
        } catch (UnknownHostException e) {
            //do noting
        }
    }

}
