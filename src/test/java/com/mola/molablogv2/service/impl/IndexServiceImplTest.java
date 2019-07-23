package com.mola.molablogv2.service.impl;

import com.mola.molablogv2.constant.ClientDescConst;
import com.mola.molablogv2.dto.client.ClientDTO;
import com.mola.molablogv2.service.client.IndexService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-11 上午11:32
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class IndexServiceImplTest {

    @Autowired
    IndexService service;

    @Test
    public void selectAll() {
        List<ClientDTO> dtoList =  service.list(0,1,4, ClientDescConst.BY_PV);
        Assert.assertNotNull(dtoList);
    }
}