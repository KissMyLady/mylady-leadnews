package top.mylady.article.ctrl.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
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
import top.mylady.model.article.dtos.UserSearchDto;
import top.mylady.model.user.pojos.ApUserSearch;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SearchTest {

    private static final Logger logger = Logger.getLogger(SearchTest.class);

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    /**
     * 增加
     */
    @Test
    public void testLoadSearchHistory() throws Exception{
        UserSearchDto dto = new UserSearchDto();
        dto.setEquipmentId(1);
        dto.setPageSize(20);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/load_search_history");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(dto));

        //设置返回参数
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    /**
     * 删除
     */
    @Test
    public void testDeleteSearchHistory() throws Exception{
        UserSearchDto dto = new UserSearchDto();
        dto.setEquipmentId(1);

        ApUserSearch apUserSearch = new ApUserSearch();
        apUserSearch.setId(8103);

        List<ApUserSearch> list = new ArrayList<>();
        list.add(apUserSearch);

        dto.setHisList(list);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/del_search");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(dto));

        //设置返回参数
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    /**
     * 清空
     */
    @Test
    public void testClearSearch() throws Exception{
        UserSearchDto dto = new UserSearchDto();
        dto.setEquipmentId(1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/clear_search");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(dto));

        //设置返回参数
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    /**
     * 今日热词
     */
    @Test
    public void testHotWords() throws Exception{
        UserSearchDto dto = new UserSearchDto();
        dto.setHotDate("2019-07-24");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/load_hot_keywords");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(dto));

        //设置返回参数
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    /**
     * 联想词查询
     */
    @Test
    public void testAssociate() throws Exception{
        UserSearchDto dto = new UserSearchDto();
        dto.setPageSize(20);
        dto.setSearchWords("黑马");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/associate_search");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(dto));

        //设置返回参数
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    /**
     * 文章搜索
     */
    @Test
    public void testEsArticleSearch() throws Exception{
        logger.info("文章搜索测试");

        UserSearchDto dto = new UserSearchDto();
        dto.setEquipmentId(1);
        dto.setPageNum(1);
        dto.setPageSize(10);
        dto.setSearchWords("面试");

        logger.debug("搜索, UserSearchDto设置完毕, 开始ctrl请求");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/search/article_search");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(dto));

        //设置返回参数
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

        logger.info("文章搜索测试结束");
    }
}
