package top.mylady.common.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;


public class JacksonTest {

    @Test
    public void testJackson(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper = ConfusionModule.registerModule(objectMapper);
        System.out.println("打印测试objectMapper对象: "+ objectMapper);
        //com.fasterxml.jackson.databind.ObjectMapper@399c4be1
    }
}
