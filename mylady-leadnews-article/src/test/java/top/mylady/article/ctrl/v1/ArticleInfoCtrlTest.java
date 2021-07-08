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
import top.mylady.model.article.dtos.ArticleInfoDto;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ArticleInfoCtrlTest {

    private static final Logger logger = Logger.getLogger(ArticleInfoCtrlTest.class);

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void testInfoArticle() throws Exception{
        ArticleInfoDto dto = new ArticleInfoDto();

        dto.setArticleId(1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/load_article_info");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(dto));

        //设置返回参数
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDetailBehavior() throws Exception{
        ArticleInfoDto dto = new ArticleInfoDto();
        dto.setAuthorId(1);
        dto.setEquipmentId(1);
        dto.setArticleId(1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/article/load_article_behavior");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsBytes(dto));
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testOnly(){
        System.out.println("only test");
    }
}
