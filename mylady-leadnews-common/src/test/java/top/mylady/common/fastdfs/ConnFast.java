package top.mylady.common.fastdfs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ConnFast {

    @Autowired
    private FastDfsConfig fastDfsConfig;

    @Test
    public void connFast(){
        System.out.println("fastClient.getClass():　"+ fastDfsConfig);

    }
}
