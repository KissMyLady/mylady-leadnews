package top.mylady.article.service.impl;

import com.aliyuncs.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.article.service.AppArticleService;
import top.mylady.common.article.constans.ArticleConstans;
import top.mylady.model.article.dtos.ArticleHomeDto;
import top.mylady.model.article.pojos.ApArticle;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.mappers.app.ApArticleMapper;
import top.mylady.model.mappers.app.ApUserArticleListMapper;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.model.user.pojos.ApUserArticleList;
import top.mylady.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;
import java.util.List;


/**
 * 实现层, 自动数据注入
 */
//@SuppressWarnings("all")  //去除警告
@Service
public class AppArticleServiceImpl implements AppArticleService {

    private static Logger logger = Logger.getLogger(AppArticleServiceImpl.class);

    private static final short MAX_PAGE_SIZE = 50;

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Autowired
    private ApUserArticleListMapper apUserArticleListMapper;

    @Override
    public ResponseResult load(Short type, ArticleHomeDto dto) {
        logger.info("打印传递过来的type, dto: 并开始进行参数校验: "+  type+ " : "+ dto);

        //参数校验
        if(dto == null ){
            dto = new ArticleHomeDto();
        }

        //时间校验
        if(dto.getMaxBehotTime() == null){
            dto.setMaxBehotTime(new Date());
        }

        if(dto.getMinBehotTime() == null){
            dto.setMinBehotTime(new Date());
        }

        //分页参数的校验
        Integer size = dto.getSize();
        if(size==null || size <= 0){
            size = 20;
        }

        size = Math.min(size, MAX_PAGE_SIZE);  //大于50的参数进行替换
        dto.setSize(size);

        //文章频道参数校验
        if(StringUtils.isEmpty(dto.getTag())){
            dto.setTag(ArticleConstans.DEFAULT_TAG);
        }

        //类型参数校验
        if(!type.equals(ArticleConstans.LOADTYPE_LOAD_MORE)&& !type.equals(ArticleConstans.LOADTYPE_LOAD_NEW)){
            type = ArticleConstans.LOADTYPE_LOAD_MORE;
        }

        //获取用户的信息
        ApUser user = AppThreadLocalUtils.getUser();
        logger.debug("参数校验完毕, 开始校验用户, 打印user: "+ user);

        //1, 判断用户是否存在
        if(user != null){
            //2, 存在 已登录 加载推荐的文章
            logger.debug("user存在, 加载推荐的文章");
            List<ApArticle> apArticleList = getUserArticle(user, dto, type);
            return ResponseResult.okResult(apArticleList);
        }else{
            logger.debug("user不存在, 加载默认的文章");
            List<ApArticle> apArticles = getDefaultArticle(dto, type);
            return ResponseResult.okResult(apArticles);
        }
    }

    /**
     * 先从用户的推荐表中查找文章信息，如果没有再从默认文章信息获取数据
     */
    private List<ApArticle> getUserArticle(ApUser user, ArticleHomeDto dto, Short type){
        try {
            List<ApUserArticleList> list = apUserArticleListMapper.loadArticleIdListByUser(user, dto, type);
            logger.debug(String.format("查找文章信息,\r\n 打印user: %s \r\n, dto: %s \r\n, type: %s", user, dto, type));
            logger.debug("存在用户 -> 查询用户文章-> 打印查询到的list: "+ list);

            if(!list.isEmpty()){
                logger.debug("推荐文章列表不为空-, 返回推荐的文章...");
                return apArticleMapper.loadArticleListByIdList(list);
            }else{
                logger.debug("推荐文章列表为空, 返回默认推荐列表...");
                return getDefaultArticle(dto, type);
            }
        }catch (Exception e){
            logger.error(String.format("先从用户的推荐表中查找文章信息，如果没有再从默认文章信息获取数据 加载错误, 错误原因e: %s ", e));
            return getDefaultArticle(dto, type);
        }

    }

    /**
     * 从默认的大文章列表中获取文章
     * @param dto 封装的dto
     * @param type 类型
     * @return 返回加载的数据
     */
    public List<ApArticle> getDefaultArticle(ArticleHomeDto dto, Short type){
        logger.debug(String.format("推荐列表为空, 查询默认文章, 打印传递的dto: %s, \r\n 打印type: %s", dto, type));
        return apArticleMapper.loadArticleListByLocation(dto, type);
    }
}
