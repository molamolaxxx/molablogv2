package com.mola.molablogv2.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: molamola
 * @Date: 19-7-19 下午5:21
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceImplTest {

    @Autowired
    private PageServiceImpl pageService;

    @Test
    public void addPreview() {
        pageService.addPreview(53);
    }

}