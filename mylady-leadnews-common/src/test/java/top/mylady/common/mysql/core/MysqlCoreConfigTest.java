package top.mylady.common.mysql.core;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.common.CommonRun;

import javax.sql.DataSource;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= CommonRun.class)
public class MysqlCoreConfigTest {

    private static Logger logger = Logger.getLogger(MysqlCoreConfigTest.class);

    @Autowired
    private DataSource mysqlCoreDataSource;

    @Test
    public void TestMysqlConn(){
        logger.debug("开始对MySQL连接进行测试......");
        logger.debug("DataSource: "+ mysqlCoreDataSource);

    }
}