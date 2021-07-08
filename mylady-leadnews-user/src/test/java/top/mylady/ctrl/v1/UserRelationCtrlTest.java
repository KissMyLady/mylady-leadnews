package top.mylady.ctrl.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import top.mylady.model.user.dtos.UserRelationDto;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.user.UserAppRun;
import top.mylady.utils.threadlocal.AppThreadLocalUtils;


@SpringBootTest(classes=UserAppRun.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserRelationCtrlTest {

    private static final Logger logger = Logger.getLogger(UserRelationCtrlTest.class);

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    /**
     * 设置线程中的用户信息
     */
    @Before
    public void setUserInfo() {
        ApUser apUser = new ApUser();
        apUser.setId(1L);
        AppThreadLocalUtils.setUser(apUser);
    }

    /**
     * 关注
     */
    @Test
    public void userFollowTest() throws Exception {
        UserRelationDto dto = new UserRelationDto();
        dto.setOperation((short) 0);
        dto.setArticleId(1);
        dto.setAuthorId(1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/user/user_follow");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(dto));
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
        logger.debug("user, 关注用户测试, 沉睡10000s");
        Thread.sleep(10000);
    }

    /**
     * 取消关注
     */
    @Test
    public void userUnFollowTest() throws Exception {
        UserRelationDto dto = new UserRelationDto();
        dto.setOperation((short) 1);
        dto.setArticleId(1);
        dto.setAuthorId(1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/user/user_follow");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(dto));
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
        logger.debug("user, 取消关注用户");
    }
}
