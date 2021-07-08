package top.mylady.common.common.contants;


/**
 * 搜索静态字段
 *  因为是App搜索, 索引前缀为app_info_,
 *      文章索引: app_info_article
 *      用户索引: app_info_user
 *      作者索引: app_info_author
 */
public class ElasticConstants {

    public static final String DEFAULT_DOC  = "_doc";
    public static final String ALL_INDEX     = "app_info_*";
    public static final String ARTICLE_INDEX = "app_info_article";
    public static final String USER_INDEX    = "app_info_user";
    public static final String AUTHOR_INDEX  = "app_info_author";

}
