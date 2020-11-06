package com.mola.molablogv2.controller.client;

import com.mola.molablogv2.common.ResponseCode;
import com.mola.molablogv2.dto.client.ClientDTO;
import com.mola.molablogv2.exception.BlogException;
import com.mola.molablogv2.exception.CommonException;
import com.mola.molablogv2.service.client.SearchService;
import com.mola.molablogv2.viewobj.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-16 下午12:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/client")
public class SearchController {

    @Autowired
    public SearchService searchService;

    @GetMapping("/search")
    public SearchVO search(@RequestParam("keyword") String keyword,
                            @RequestParam("userId") Integer userId){


        //1.通过关键字查找对应博客
        List<ClientDTO> result = null;
        try {
            result = searchService.search(keyword, userId);
        } catch (CommonException | BlogException e) {
            return new SearchVO(ResponseCode.ERROR.getCode(),e.toString());
        }

        //1.拼接
        SearchVO searchVO = new SearchVO();
        searchVO.setData(result);
        searchVO.setKeyword(keyword);
        searchVO.setCode(ResponseCode.SUCCESS.getCode());
        searchVO.setMsg(ResponseCode.SUCCESS.getDesc());

        return searchVO;
    }
}
