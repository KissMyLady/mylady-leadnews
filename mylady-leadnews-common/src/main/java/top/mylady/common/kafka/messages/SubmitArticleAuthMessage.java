package top.mylady.common.kafka.messages;
import top.mylady.common.kafka.KafkaMessage;
import top.mylady.model.mess.admin.SubmitArticleAuto;


/**
 * 继承, 主要功能是再做一次封装, 可以针对单通道和多通道进行区分
 */
public class SubmitArticleAuthMessage extends KafkaMessage<SubmitArticleAuto> {

    public SubmitArticleAuthMessage(){}

    public SubmitArticleAuthMessage(SubmitArticleAuto data){
        super(data);
    }

    @Override
    public String getType() {
        return "submit-article-auth";
    }
}
