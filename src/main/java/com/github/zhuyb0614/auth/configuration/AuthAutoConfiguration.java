package com.github.zhuyb0614.auth.configuration;

import com.github.zhuyb0614.auth.AuthProperties;
import com.github.zhuyb0614.auth.AuthJudgeChain;
import com.github.zhuyb0614.auth.interceptor.AuthInterceptor;
import com.github.zhuyb0614.auth.spi.BaseAuthJudgeNode;
import com.github.zhuyb0614.auth.spi.AfterJudgeHandler;
import com.github.zhuyb0614.auth.spi.TokenDecipher;
import com.github.zhuyb0614.auth.spi.impl.AnnotationJudge;
import com.github.zhuyb0614.auth.spi.impl.DefaultAfterJudgeHandler;
import com.github.zhuyb0614.auth.spi.impl.UriPrefixJudge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;
import java.util.List;

/**
 * @author yunbo.zhu
 * @version 1.0
 * @date 2022/5/5 4:38 下午
 */
@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnWebApplication(
        type = ConditionalOnWebApplication.Type.SERVLET
)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
@ConditionalOnBean(TokenDecipher.class)
@ConditionalOnProperty(name = "yb.auth.open-switch", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
@EnableConfigurationProperties(AuthProperties.class)
@Slf4j
public class AuthAutoConfiguration implements WebMvcConfigurer {

    @Value("${server.servlet.context-path:}")
    protected String contentPath;
    @Autowired
    private AfterJudgeHandler afterJudgeHandler;
    @Autowired(required = false)
    private List<BaseAuthJudgeNode> needLoginJudgeNodes;
    @Autowired
    private AuthProperties authProperties;

    protected AuthJudgeChain buildLoginJudgeChain() {
        log.info(">>>>>> auth auto config properties {}", authProperties);
        AuthJudgeChain authJudgeChain = new AuthJudgeChain();
        if (!CollectionUtils.isEmpty(needLoginJudgeNodes)) {
            for (BaseAuthJudgeNode loginJudgeNode : needLoginJudgeNodes) {
                authJudgeChain.addNode(loginJudgeNode);
                logLoginJudge(loginJudgeNode.getClass().getSimpleName());
            }
        }
        if (authProperties.isEnableAnnotationValid()) {
            AnnotationJudge annotationJudge = new AnnotationJudge();
            authJudgeChain.addNode(annotationJudge);
            logLoginJudge(annotationJudge.getClass().getSimpleName());
        }
        if (authProperties.isEnableUriPrefixValid()) {
            authProperties.setAuthUriPrefix(contentPath + authProperties.getAuthUriPrefix());
            authProperties.setAuthOptionalUriPrefix(contentPath + authProperties.getAuthOptionalUriPrefix());
            UriPrefixJudge uriPrefixJudge = new UriPrefixJudge(authProperties);
            authJudgeChain.addNode(uriPrefixJudge);
            logLoginJudge(uriPrefixJudge.getClass().getSimpleName());
        }
        return authJudgeChain;
    }

    private void logLoginJudge(String simpleName) {
        log.info(">>>>>>  auth judge node add {}", simpleName);
    }

    @ConditionalOnMissingBean
    @ConditionalOnBean(TokenDecipher.class)
    @Bean
    @SuppressWarnings("all")
    public AfterJudgeHandler loginHandler(TokenDecipher tokenDecipher) {
        return new DefaultAfterJudgeHandler(tokenDecipher);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(buildLoginJudgeChain(), afterJudgeHandler));
        log.info(">>>>>>  AuthInterceptor  init success");
    }

}