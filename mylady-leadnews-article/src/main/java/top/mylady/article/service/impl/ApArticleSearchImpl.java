package top.mylady.article.service.impl;

import com.aliyuncs.utils.StringUtils;
import io.searchbox.core.Search;
import io.searchbox.client.JestClient;
import io.searchbox.core.SearchResult;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.article.service.ApArticleSearchService;
import top.mylady.common.zookeeper.sequence.Sequences;
import top.mylady.model.article.dtos.UserSearchDto;
import top.mylady.model.article.pojos.ApArticle;
import top.mylady.model.article.pojos.ApAssociateWords;
import top.mylady.model.article.pojos.ApHotWords;
import top.mylady.model.behavior.pojos.ApBehaviorEntry;
import top.mylady.model.common.constants.ESIndexConstants;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
import top.mylady.model.mappers.app.*;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.model.user.pojos.ApUserSearch;
import top.mylady.utils.threadlocal.AppThreadLocalUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 搜索
 */
@Service
public class ApArticleSearchImpl implements ApArticleSearchService {

    private static final Logger logger = Logger.getLogger(ApArticleSearchImpl.class);

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private ApUserSearchMapper apUserSearchMapper;


    public ResponseResult getEntryId(UserSearchDto dto){
        ApUser user = AppThreadLocalUtils.getUser();

        // 用户和设备不能同时为空
        if(user == null && dto.getEquipmentId()==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        Long userId = null;
        if(user!=null){
            userId = user.getId();
        }

        ApBehaviorEntry apBehaviorEntry = null;
        try {
            apBehaviorEntry = apBehaviorEntryMapper.selectByUserIdOrEquipment(userId, dto.getEquipmentId());
        }catch (Exception e){
            logger.error(String.format("Search: 加载错误, 原因是: %s ", e));
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
        }

        // 行为实体找以及注册了，逻辑上这里是必定有值得，除非参数错误
        if(apBehaviorEntry==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        return ResponseResult.okResult(apBehaviorEntry.getId());
    }

    /**
     * 搜索历史记录
     */
    @Override
    public ResponseResult findUserSearchHistory(UserSearchDto dto) {

        logger.debug("Search service层, 打印ctrl传递过来的UserSearchDto dto: "+ dto);

        //1, 判断传入参数是否合法
        if(dto.getPageSize() > 50 || dto.getPageSize() < 0){
            logger.warn("Search: 传入dto参数不合法, 返回错误");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //先获取行为实体id
        ResponseResult result = getEntryId(dto);
        if(result.getCode() != AppHttpCodeEnum.SUCCESS.getCode()){
            logger.info("Search: 能查询到数据, 返回");
            return result;
        }

        //查询搜索记录
        List<ApUserSearch> list = apUserSearchMapper.selectSearchHistory((int)result.getData(), dto.getPageSize());

        return ResponseResult.okResult(list);
    }

    /**
     * 删除历史记录
     */
    @Override
    public ResponseResult delUserSearch(UserSearchDto dto) {

        if(dto.getHisList()==null || dto.getHisList().size() <= 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //获取行为实体id
        ResponseResult result = getEntryId(dto);
        if(result.getCode()!=AppHttpCodeEnum.SUCCESS.getCode()){
            return result;
        }

        List<Integer> ids = dto.getHisList().stream().map(ApUserSearch::getId).collect(Collectors.toList());

        //通过id修改状态
        int count = apUserSearchMapper.deleteUserSearchHistory((int)result.getData(), ids);
        return ResponseResult.okResult(count);
    }

    /**
     * 清空搜索记录
     */
    @Override
    public ResponseResult clearUserSearch(UserSearchDto dto) {
        ResponseResult result = getEntryId(dto);

        if(result.getCode()!=AppHttpCodeEnum.SUCCESS.getCode()){
            return result;
        }

        int count = apUserSearchMapper.clearUserSearch((int) result.getData());
        return ResponseResult.okResult(count);
    }

    @Autowired
    private ApHotWordsMapper apHotWordsMapper;

    /**
     * 今日热词
     */
    @Override
    public ResponseResult hotKeyWords(String date) {
        if(StringUtils.isEmpty(date)){
            date = DateFormatUtils.format(new Date(),"yyyy-MM-dd");
        }
        logger.debug("Search: 今日热词, 打印传递过去的Date值: "+ date);
        List<ApHotWords> apHotWords = apHotWordsMapper.queryByHotDate(date);
        return ResponseResult.okResult(apHotWords);
    }

    @Autowired
    private ApAssociateWordsMapper apAssociateWordsMapper;

    /**
     * 联想词查询
     */
    @Override
    public ResponseResult searchAssociate(UserSearchDto dto) {
        if(dto.getPageSize() > 50 || dto.getPageSize()<1){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        List<ApAssociateWords> apAssociateWords = apAssociateWordsMapper.selectByAssociateWords(
                "%"+ dto.getSearchWords()+ "%",
                dto.getPageSize());

        return ResponseResult.okResult(apAssociateWords);
    }

    @Autowired
    private JestClient jestClient;

    @Autowired
    private ApArticleMapper apArticleMapper;

    /**
     * Es文章分页查询
     */
    @Override
    public ResponseResult esArticleSearch(UserSearchDto dto) {
        logger.debug("Es文章分页查询, 进入, 打印传递过来的dto: "+ dto);

        //保存搜索记录,只在第一页查询的时候进行保存
        if(dto.getFromIndex() == 0){
            logger.debug("Es文章分页查询. 保存搜索记录,只在第一页查询的时候进行保存");
            ResponseResult ret = getEntryId(dto);
            if(ret.getCode()!=AppHttpCodeEnum.SUCCESS.getCode()){
                return ret;
            }else {
                logger.debug("Es文章分页查询, 返回结果不为success");
            }
            saveUserSearchHistory((int)ret.getData(),dto.getSearchWords());
        }

        //构建查询条件  jestClient
        //按照关键字查询，分页查询
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", dto.getSearchWords()));
        searchSourceBuilder.from(dto.getFromIndex());
        searchSourceBuilder.size(dto.getPageSize());

        logger.debug("Es文章分页查询, 分页查询");

        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(ESIndexConstants.ARTICLE_INDEX)
                .addType(ESIndexConstants.DEFAULT_DOC).build();
        try {
            logger.debug("Es文章分页查询, 进入try部分");
            SearchResult searchResult = jestClient.execute(search);
            List<ApArticle> apArticles = searchResult.getSourceAsObjectList(ApArticle.class);
            List<ApArticle> resultList = new ArrayList<>();
            for (ApArticle apArticle : apArticles) {
                apArticle = apArticleMapper.selectById(Long.valueOf(apArticle.getId()));
                if(apArticle==null){
                    continue;
                }
                resultList.add(apArticle);
            }
            //获取结果
            return ResponseResult.okResult(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

    @Autowired
    private Sequences sequences;

    /**
     * 保存用户搜索记录
     */
    @Override
    public ResponseResult saveUserSearchHistory(Integer entryId, String searchWords) {

        //去检查当前数据是否存在
        int count = apUserSearchMapper.checkExistSearchHistory(entryId, searchWords);

        if(count > 0){
            logger.debug("保存用户搜索记录, 检查当前搜索数据存在, 返回ok");
            return ResponseResult.okResult(1);
        }

        //不存在，保存
        ApUserSearch apUserSearch = new ApUserSearch();
        int idNum = sequences.sequenceApSearchHistoryId().intValue();  //注意这里需要自定义增长id, 不然报错
        logger.debug("插入序列id号idNum是: "+ idNum);

        apUserSearch.setId(idNum);
        apUserSearch.setEntryId(entryId);
        apUserSearch.setKeyword(searchWords);
        apUserSearch.setStatus(1);
        apUserSearch.setCreatedTime(new Date());

        logger.debug("253行, ApUserSearch 保存用户搜索记录, 开始插入, 打印插入数据apUserSearch: "+ apUserSearch);
        int insert = 0;

        try {
            insert = apUserSearchMapper.insertSearchHistory(apUserSearch);
        }catch (Exception e){
            logger.error(String.format("数据插入错误, 原因是: %s ", e));
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        logger.debug("ApUserSearch 保存用户搜索记录, 插入成功");
        return ResponseResult.okResult(insert);

    }
}
