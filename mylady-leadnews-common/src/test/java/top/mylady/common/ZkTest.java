package top.mylady.common;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.common.zookeeper.sequence.Sequences;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=CommonRun.class)
public class ZkTest {

    private static final Logger logger = Logger.getLogger(ZkTest.class);

    @Autowired
    private Sequences sequences;

    @Test
    public void testZookeeper(){
        //logger.debug("打印sequences: "+ sequences);

        for (int i = 0; i < 50; i++) {
            Long along = sequences.sequenceApReadBehavior();
            System.out.println("打印分布式自增主键along: "+ along);
        }

    }
}
