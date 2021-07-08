package top.mylady.common.zookeeper.sequence;

import top.mylady.common.zookeeper.ZookeeperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Sequences {

    @Autowired
    private ZookeeperClient client;

    public Long sequenceApLikes(){
        return this.client.sequence(ZkSequenceEnum.AP_LIKES);
    }

    public Long sequenceApReadBehavior(){
        return this.client.sequence(ZkSequenceEnum.AP_READ_BEHAVIOR);
    }

    public Long sequenceApSearchHistoryId(){
        return this.client.sequence(ZkSequenceEnum.AP_ARTICLE_SEARCH_AUTO_INCREASE_ID);
    }

    public Long sequenceApCollection(){
        return this.client.sequence(ZkSequenceEnum.AP_COLLECTION);
    }

    public Long sequenceApUserFollow(){
        return this.client.sequence(ZkSequenceEnum.AP_USER_FOLLOW);
    }

    public Long sequenceApUserFan(){
        return this.client.sequence(ZkSequenceEnum.AP_USER_FAN);
    }
}
