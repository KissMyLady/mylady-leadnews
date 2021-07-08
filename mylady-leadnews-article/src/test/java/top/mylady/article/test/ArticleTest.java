package top.mylady.article.test;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.article.ArticleJarApplication;
import top.mylady.article.service.AppArticleService;
import top.mylady.common.article.constans.ArticleConstans;
import top.mylady.model.article.dtos.ArticleHomeDto;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.utils.threadlocal.AppThreadLocalUtils;


@SpringBootTest(classes= ArticleJarApplication.class)
@RunWith(SpringRunner.class)
public class ArticleTest {

    private static Logger logger = Logger.getLogger(ArticleTest.class);

    @Autowired
    private AppArticleService appArticleService;

    /**
     * 文章load测试
     */
    @Test
    public void testArticle(){
        try {
            ApUser apUser = new ApUser();
            apUser.setId(1l);
            AppThreadLocalUtils.setUser(apUser);

            ArticleHomeDto dto = new ArticleHomeDto();
            dto.setSize(1);
            dto.setTag("1");

            ResponseResult data = appArticleService.load(ArticleConstans.LOADTYPE_LOAD_NEW, dto);
            System.out.println(data.getData());

        }catch (Exception e){
           logger.error(String.format("加载错误, 错误原因e: %s ", e));
        }
    }

}
