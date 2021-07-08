package top.mylady.common.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import top.mylady.common.kafka.messages.SubmitArticleAuthMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;


@Component
public class KafkaSender {

    Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    KafkaTopicConfig kafkaTopicConfig;

    /**
     * 发送一个消息
     */
    public void sendMesssage(String topic,String key,KafkaMessage<?> message){
        try {
            this.kafkaTemplate.send(topic, key, mapper.writeValueAsString(message));
        }catch (Exception e){
            logger.error("send message to [{}] error:",topic,e);
        }
    }

    /**
     * 发送一个不包装的消息
     * 只能是内部使用，拒绝业务上使用
     */
    public void sendMesssageNoWrap(String topic,String key,String message){
        try {
            this.kafkaTemplate.send(topic, key, message);
        }catch (Exception e){
            logger.error("send message to [{}] error:",topic,e);
        }
    }

    /**
     * 发送审核文章的消息
     */
    public void sendSubmitArticleAuthMessage(SubmitArticleAuthMessage message){
        this.sendMesssage(kafkaTopicConfig.getSubmitArticleAuth(), UUID.randomUUID().toString(), message);
    }
}