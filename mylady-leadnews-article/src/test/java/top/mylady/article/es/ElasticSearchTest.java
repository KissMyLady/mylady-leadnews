package top.mylady.article.es;


import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.common.common.pojo.EsIndexEntity;
import top.mylady.model.article.dtos.ArticleHomeDto;
import top.mylady.model.article.pojos.ApArticle;
import top.mylady.model.article.pojos.ApArticleContent;
import top.mylady.model.common.constants.ESIndexConstants;
import top.mylady.model.crawler.core.parse.ZipUtils;
import top.mylady.model.mappers.app.ApArticleContentMapper;
import top.mylady.model.mappers.app.ApArticleMapper;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchTest {

    private static final Logger logger = Logger.getLogger(ElasticSearchTest.class);

    @Autowired
    private JestClient jestClient;

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    /**
     * Es测试代码. 后期审核文章会自动添加索引库
     */
    @Test
    public void testSave() throws Exception {

        ArticleHomeDto dto = new ArticleHomeDto();
        dto.setSize(50);
        dto.setTag("__all__");

        List<ApArticle> apArticles = apArticleMapper.loadArticleListByLocation(dto, null);
        logger.debug("Article: 开始搜索测试");

        for (ApArticle apArticle : apArticles) {
            ApArticleContent apArticleContent = apArticleContentMapper.selectByArticleId(apArticle.getId());

            EsIndexEntity esIndexEntity = new EsIndexEntity();
            esIndexEntity.setChannelId(new Long(apArticle.getChannelId()));
            esIndexEntity.setId(apArticle.getId().longValue());
            esIndexEntity.setContent(ZipUtils.gunzip(apArticleContent.getContent()));
            esIndexEntity.setPublishTime(apArticle.getPublishTime());
            esIndexEntity.setStatus(new Long(1));
            esIndexEntity.setTag("article");
            esIndexEntity.setTitle(apArticle.getTitle());

            Index.Builder builder = new Index.Builder(esIndexEntity);
            builder.id(apArticle.getId().toString());
            builder.refresh(true);
            Index index = builder.index(ESIndexConstants.ARTICLE_INDEX).type(ESIndexConstants.DEFAULT_DOC).build();
            JestResult result = jestClient.execute(index);
            if (result != null && !result.isSucceeded()) {
                throw new RuntimeException(result.getErrorMessage() + "插入更新索引失败!");
            }
        }

        logger.debug("Article: 结束搜索测试");

    }

}