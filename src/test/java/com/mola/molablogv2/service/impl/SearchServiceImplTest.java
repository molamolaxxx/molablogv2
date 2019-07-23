package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.service.client.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: molamola
 * @Date: 19-7-22 上午9:55
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchServiceImplTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void search() {
        searchService.search("spring",0);
    }
}