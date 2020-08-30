package com.mola.molablogv2.aspect;

import com.mola.molablogv2.annotation.SendEmail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: molamola
 * @Date: 19-7-31 上午11:12
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailAspectTest {

    @Test
    @SendEmail
    public void send(){
        System.out.println("233");
    }

}