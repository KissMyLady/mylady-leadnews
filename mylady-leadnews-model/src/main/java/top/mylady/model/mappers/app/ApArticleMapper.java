package top.mylady.model.mappers.app;

import top.mylady.model.article.dtos.ArticleHomeDto;
import top.mylady.model.article.pojos.ApArticle;
import top.mylady.model.user.pojos.ApUserArticleList;
import org.apache.ibatis.annotations.Param;
import java.util.List;


public interface ApArticleMapper {

    //@Param: sql语句参数对应
    /**
     * 按照用户地理位置, 加载文章
     * @param dto 参数封装对象
     * @param type 加载方向
     * @return 列表数据
     */
    List<ApArticle> loadArticleListByLocation(@Param("dto") ArticleHomeDto dto, @Param("type") Short type);

    /**
     * 依据文章IDS来获取文章详细内容
     * @param list 文章ID
     * @return 列表数据
     */
    List<ApArticle> loadArticleListByIdList(List<ApUserArticleList> list);

    /**
     *  ?
     */
    ApArticle selectById(Long id);


    void insertApArticle(ApArticle apArticle);

}
