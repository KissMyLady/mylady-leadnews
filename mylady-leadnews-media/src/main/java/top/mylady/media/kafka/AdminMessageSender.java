package top.mylady.media.kafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.mylady.common.kafka.KafkaSender;
import top.mylady.common.kafka.messages.SubmitArticleAuthMessage;


@Component
public class AdminMessageSender {

    @Autowired
    private KafkaSender kafkaSender;

    public void sendMessage(SubmitArticleAuthMessage message){
        kafkaSender.sendSubmitArticleAuthMessage(message);
    }

}
