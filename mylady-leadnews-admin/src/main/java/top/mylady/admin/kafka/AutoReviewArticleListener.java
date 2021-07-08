package top.mylady.admin.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.apache.log4j.Logger;
import top.mylady.admin.service.ReviewMediaArticleService;
import top.mylady.common.kafka.KafkaListener;
import top.mylady.common.kafka.KafkaTopicConfig;
import top.mylady.common.kafka.messages.SubmitArticleAuthMessage;
import top.mylady.model.mess.admin.SubmitArticleAuto;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Log4j2
@Component
public class AutoReviewArticleListener implements KafkaListener<String,String> {

   // private static final Logger logger = Logger.getLogger(AutoReviewArticleListener.class);

    @Autowired
    private KafkaTopicConfig kafkaTopicConfig;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ReviewMediaArticleService reviewMediaArticleService;

    @Override
    public String topic() {
        return kafkaTopicConfig.getSubmitArticleAuth();
    }

    @Override
    public void onMessage(ConsumerRecord<String, String> consumerRecord, Consumer<?, ?> consumer) {
        String value = consumerRecord.value();
        log.info("接收到的消息为：{}"+ value);

        try {
            SubmitArticleAuthMessage message = mapper.readValue(value, SubmitArticleAuthMessage.class);
            if(message != null){
                SubmitArticleAuto.ArticleType type = message.getData().getType();
                if(type == SubmitArticleAuto.ArticleType.WEMEDIA){
                    Integer articleId = message.getData().getArticleId();
                    if(articleId != null){
                        //审核文章信息
                        reviewMediaArticleService.autoReviewArticleByMedia(articleId);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("处理自动审核文章错误:[{}],{}",value,e);
            throw new RuntimeException("WS消息处理错误",e);
        }
    }
}
