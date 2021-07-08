package top.mylady.behavior.ctrl.v1;

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
import top.mylady.model.behavior.dtos.LikesBehaviorDto;
import top.mylady.model.behavior.dtos.UnLikesBehaviorDto;
import top.mylady.model.behavior.pojos.ApUnlikesBehavior;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class LikeAndDislikeTest {

    private static final Logger logger = Logger.getLogger(LikeAndDislikeTest.class);

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Before
    public void setUser(){
        ApUser apUser = new ApUser();
        apUser.setId(1L);
        AppThreadLocalUtils.setUser(apUser);
        System.out.println("Pass");
    }

    /**
     * 点赞测试
     */
    @Test
    public void testLike() throws Exception{
        logger.info("点赞测试");
        LikesBehaviorDto dto = new LikesBehaviorDto();
        dto.setEntryId(1);            //文章, 动态, 评论等Id
        dto.setEquipmentId(2);        //设备Id, [数据库只有1, 2设备], 服务层没有user的情况下, 是通过设备id查询的
        dto.setOperation((short) 1);  //点赞类型: 0: 点赞, 1: 取消点赞
        dto.setType((short) 0);       //点赞内容类型: 0: 文章, 1: 动态, 2: 评论

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/behavior/like_behavior");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(dto));

        //设置返回参数
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

        /* select * from ap_likes_behavior;
         * +----+-------------------+----------+------+-----------+---------------------+-------+
         * | id | behavior_entry_id | entry_id | type | operation | created_time        | burst |
         * +----+-------------------+----------+------+-----------+---------------------+-------+
         * |  3 |                 3 |        1 |    0 |         1 | 2021-06-27 22:06:01 | 3-3   |
         * +----+-------------------+----------+------+-----------+---------------------+-------+
        * */
    }

    /**
     * 不喜欢测试
     */
    @Test
    public void testDisLike() throws Exception{
        logger.info("不喜欢测试");

        UnLikesBehaviorDto dto = new UnLikesBehaviorDto();
        //dto.setEquipmentId(1);   //设备Id, [数据库只有1, 2设备], 服务层没有user的情况下, 是通过设备id查询的
        dto.setType((short) 0);  //类型: 0: 不喜欢, 1. 取消不喜欢
        dto.setArticleId(11001);     //文章id
        logger.debug("Behavior Test: 不喜欢测试, 打印传输对象dto: "+ dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/behavior/unlike_behavior");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(dto));

        //设置返回参数
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

}
