package top.mylady.model.crawler.core.callback;
import top.mylady.model.crawler.core.proxy.CrawlerProxy;
import java.util.List;


/**
 * IP池更新回调
 */
public interface ProxyProviderCallBack {
    public List<CrawlerProxy> getProxyList();
}