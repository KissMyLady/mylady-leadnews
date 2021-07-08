package top.mylady.common.zookeeper.sequence;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 * 封装运行时每个表对应的自增器
 */
public class ZkSequence {

    RetryPolicy retryPolicy = new ExponentialBackoffRetry(500,3);

    DistributedAtomicLong distAtomicLong;

    //构造器
    public ZkSequence(CuratorFramework client, String counterPath){
        this.distAtomicLong = distAtomicLong = new DistributedAtomicLong(client,counterPath,retryPolicy);
    }

    /**
     * 生成序列
     */
    public Long sequence() throws Exception {
        AtomicValue<Long> increment = distAtomicLong.increment();
        if(increment.succeeded()){
            return increment.postValue();
        }else{
            return null;
        }
    }
}
