package top.mylady.admin.kafka;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaTest {

    private static final Logger logger = Logger.getLogger(KafkaTest.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Test
    public void Hello(){
        logger.debug("Hello Test");
    }

    @Test
    public void test(){

        String topic = "topic.test";
        String alKey = "";
        String sendData = "adsadt";

        try {
            logger.info("测试kafka消息队列, 开始发送消息......");
            kafkaTemplate.send(topic, alKey, sendData);
            System.out.println("==========消息发送了============");
            Thread.sleep(5000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
