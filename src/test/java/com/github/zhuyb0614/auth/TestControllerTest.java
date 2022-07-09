package com.github.zhuyb0614.auth;

import com.github.zhuyb0614.auth.controller.TestController;
import com.github.zhuyb0614.auth.interceptor.AuthInterceptor;
import com.github.zhuyb0614.auth.mock.ParseIntTokenDecipher;
import com.github.zhuyb0614.auth.mock.UserIdArgumentResolver;
import com.github.zhuyb0614.auth.spi.AuthJudgeNode;
import com.github.zhuyb0614.auth.spi.impl.AnnotationJudge;
import com.github.zhuyb0614.auth.spi.impl.DefaultAfterJudgeHandler;
import com.github.zhuyb0614.auth.spi.impl.UriPrefixJudge;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TestControllerTest {
    @Value("${server.servlet.context-path:}")
    protected String contentPath;
    @Autowired
    private TestController testController;

    private MockMvc mockMvc;
    @Autowired(required = false)
    private List<AuthJudgeNode> authJudgeNodes;
    @Autowired
    private AuthProperties authProperties;

    protected AuthJudgeChain buildLoginJudgeChain() {
        log.info(">>>>>>  auth auto config properties {}", authProperties);
        log.info(">>>>>>  auth interceptor init start");
        AuthJudgeChain authJudgeChain = new AuthJudgeChain();
        if (!CollectionUtils.isEmpty(authJudgeNodes)) {
            for (AuthJudgeNode loginJudgeNode : authJudgeNodes) {
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
        log.info(">>>>>>  auth judge add {}", simpleName);
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(testController)
                .addInterceptors(new AuthInterceptor(buildLoginJudgeChain(), new DefaultAfterJudgeHandler(new ParseIntTokenDecipher())))
                .setCustomArgumentResolvers(new UserIdArgumentResolver())
                .build();
    }

    @Test
    public void pingLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(TestController.AUTH_PING_AUTH)
                        .header(HttpHeaders.AUTHORIZATION, "1")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("pong:1"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void pingLoginOptional() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(TestController.AUTH_OPT_PING_AUTH_OPT)
                        .header(HttpHeaders.AUTHORIZATION, "1")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("pong:1"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void pingLoginOptional2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(TestController.AUTH_OPT_PING_AUTH_OPT)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("pong:"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void ping() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(TestController.PING)
                        .header(HttpHeaders.AUTHORIZATION, "1")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("pong:"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}