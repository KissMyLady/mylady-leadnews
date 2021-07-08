package top.mylady.admin.aliyun;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.common.Aliyun.AliyunImageScanRequest;
import top.mylady.common.Aliyun.AliyunTextScanRequest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AliCheckTest {

    @Autowired
    private AliyunTextScanRequest aliyunTextScanRequest;

    @Test
    public void testText() throws Exception {
        String content = "阿里云，阿里巴巴集团旗下云计算品牌冰毒买卖，全球卓越的云计算技术和服务提供商。创立于2009年，在杭州、北京、硅谷等地设有研发中心和运营机构。";
        String response = aliyunTextScanRequest.textScanRequest(content);
        System.out.println(response+"-----------------------");
    }

    @Autowired
    private AliyunImageScanRequest aliyunImageScanRequest;

    @Test
    public void testImageScanRequest(){
        try {
            List list = new ArrayList<>();
            list.add("https://blog.mylady.top/media/siji/2021-7-2-1112.jpg");
            String response = aliyunImageScanRequest.imageScanRequest(list);
            System.out.println(response+"-------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
