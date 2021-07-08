package top.mylady.model.mappers.app;

import top.mylady.model.article.dtos.ArticleHomeDto;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.model.user.pojos.ApUserArticleList;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ApUserArticleListMapper {
    List<ApUserArticleList> loadArticleIdListByUser(@Param("user") ApUser user,
                                                    @Param("dto") ArticleHomeDto dto,
                                                    @Param("type") Short type);
}
