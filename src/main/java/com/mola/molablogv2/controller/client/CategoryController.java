package com.mola.molablogv2.controller.client;

import com.mola.molablogv2.common.ResponseCode;
import com.mola.molablogv2.common.ServerResponse;
import com.mola.molablogv2.dto.client.ClientDTO;
import com.mola.molablogv2.exception.ClientException;
import com.mola.molablogv2.service.client.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-13 下午3:55
 * @Version 1.0
 * category.html页面controller
 */
@RestController
@RequestMapping("/client")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    private ServerResponse list(@RequestParam("userId") Integer userId){

        List<ClientDTO> result = null;
        try {
            result = categoryService.list(userId);
        } catch (ClientException e) {
            ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getDesc(),result);
    }
}
