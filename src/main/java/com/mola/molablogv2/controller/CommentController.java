package com.mola.molablogv2.controller;

import com.mola.molablogv2.common.ResponseCode;
import com.mola.molablogv2.common.ServerResponse;
import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.exception.CommentException;
import com.mola.molablogv2.service.BlogCommentService;
import com.mola.molablogv2.viewobj.ManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: molamola
 * @Date: 19-7-8 下午4:01
 * @Version 1.0
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private BlogCommentService commentService;

    @GetMapping("/{blogId}")
    public ManageVO selectAll(@PathVariable("blogId")Integer blogId,
                               @RequestParam("offset") Integer offset,
                               @RequestParam("limit") Integer limit){

        //1.根据博客id查找comment
        ManageDTO dto = null;
        try {
            dto = commentService.list(blogId,offset,limit);
        } catch (CommentException e) {
            return new ManageVO(e.getCode(),e.getMessage());
        }

        //2.拼装vo
        ManageVO commentVO = new ManageVO();
        commentVO.setData(dto.getData());
        commentVO.setCount(dto.getTotalCount());
        commentVO.setMsg(ResponseCode.SUCCESS.getDesc());
        commentVO.setCode(ResponseCode.SUCCESS.getCode());

        return commentVO;
    }

    @DeleteMapping("/{id}")
    public ServerResponse delete(@PathVariable("id") Integer id){

        try {
            commentService.delete(id);
        } catch (CommentException e) {
            return ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        return ServerResponse.createBySuccess();
    }
}
