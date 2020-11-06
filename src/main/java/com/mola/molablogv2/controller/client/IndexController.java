package com.mola.molablogv2.controller.client;

import com.mola.molablogv2.common.ResponseCode;
import com.mola.molablogv2.common.ServerResponse;
import com.mola.molablogv2.dto.client.ClientDTO;
import com.mola.molablogv2.exception.ClientException;
import com.mola.molablogv2.service.client.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-11 上午10:31
 * @Version 1.0
 * index.html页面controller
 */
@RestController
@RequestMapping("/client")
@Slf4j
public class IndexController {

    @Autowired
    public IndexService indexService;

    @GetMapping("/index")
    public ServerResponse list(@RequestParam("userId") Integer userId,
                                     @RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit,
                                     @RequestParam("descType") Integer descType){

        List<ClientDTO> result = null;
        try {
            result = indexService.list(userId,offset,limit,descType);
        } catch (ClientException e) {
            ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getDesc(),result);
    }

}
