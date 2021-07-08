package top.mylady.common.kafka;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.common.Aliyun.AliyunImageScanRequest;
import top.mylady.common.Aliyun.AliyunTextScanRequest;
import top.mylady.common.CommonRun;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= CommonRun.class)
public class KafKaTest {

    private static final Logger logger = Logger.getLogger(KafKaTest.class);

    @Autowired
    private AliyunTextScanRequest aliyunTextScanRequest;

    @Autowired
    private AliyunImageScanRequest aliyunImageScanRequest;

    /*
    * 文本检测
    * */
    @Test
    public void TestSafeScan() throws Exception {
        String message = "";
        String response = aliyunTextScanRequest.textScanRequest(message);
        logger.debug("云检查扫描文本是否含有违规信息: "+ response);
    }

    /*
    * 图片检测
    * */
    @Test
    public void TestSafeImage() throws Exception{
        List list = new ArrayList<>();
        list.add("https://blog.mylady.top/media/siji/2021-7-2-1112.jpg");
        String response = aliyunImageScanRequest.imageScanRequest(list);
        logger.debug("云检查扫描图片列表是否含有违规信息: "+ response);
    }
}
