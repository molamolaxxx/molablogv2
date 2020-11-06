package com.mola.molablogv2.controller;

import com.mola.molablogv2.common.ServerResponse;
import com.mola.molablogv2.dto.BlogTypeDTO;
import com.mola.molablogv2.emun.SessionErrorEmun;
import com.mola.molablogv2.exception.TypeException;
import com.mola.molablogv2.service.BlogTypeService;
import com.mola.molablogv2.utils.ValidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-6-27 下午1:44
 * @Version 1.0
 * blogtype的controller
 */
@RestController
@RequestMapping("/type")
@Slf4j
public class TypeController {

    @Autowired
    private BlogTypeService blogTypeService;

    @GetMapping
    public ServerResponse selectAll(@RequestParam("userId")Integer userId , HttpSession session ,
                                     HttpServletResponse response){

        //判断用户是否正确
        if (!ValidUtil.userValid(response,session,userId))
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.USERID_ERROR.getCode(),
                    SessionErrorEmun.USERID_ERROR.getMsg());

        //1.根据userId查找所有type，以dto传到前端
        BlogTypeDTO blogTypeDTO = blogTypeService.selectAllByUserId(userId);

        //2.转化成DTO
        List<BlogTypeDTO> blogTypeDTOList = blogTypeDTO.getChildren();

        return ServerResponse.createBySuccess(blogTypeDTOList);
    }

    @GetMapping("/{id}")
    public ServerResponse selectOne(@PathVariable("id") Integer id ,HttpServletResponse response){

        BlogTypeDTO blogTypeDTO = null;

        try {
            blogTypeDTO = blogTypeService.selectOneById(id);
        } catch (TypeException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        return ServerResponse.createBySuccess(blogTypeDTO);
    }

    @PutMapping("/update")
    public ServerResponse update(@RequestBody BlogTypeDTO data, HttpSession session ,HttpServletResponse response){

        //判断用户是否正确
        if (!ValidUtil.userValid(response,session,data.getUserId()))
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.USERID_ERROR.getCode(),
                    SessionErrorEmun.USERID_ERROR.getMsg());

        //非空校验
        if (null == data.getId()|| null == data.getClassName() || null == data.getUserId())
        {
            log.error("insert:分类信息缺失");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.TYPE_INFO_EMPTY.getCode(),
                    SessionErrorEmun.TYPE_INFO_EMPTY.getMsg());
        }
        try {
            blogTypeService.update(data);
        } catch (TypeException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        return ServerResponse.createBySuccess();
    }

    @PostMapping("/insert")
    public ServerResponse insert(@RequestBody BlogTypeDTO data , HttpSession session , HttpServletResponse response){

        //判断用户是否正确
        if (!ValidUtil.userValid(response,session,data.getUserId()))
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.USERID_ERROR.getCode(),
                    SessionErrorEmun.USERID_ERROR.getMsg());

        //非空校验
        if (null == data.getClassName() || null == data.getParentId() || null == data.getUserId())
        {
            log.error("insert:分类信息缺失");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.TYPE_INFO_EMPTY.getCode(),
                    SessionErrorEmun.TYPE_INFO_EMPTY.getMsg());
        }

        //调用service
        try {
            blogTypeService.insert(data);
        } catch (TypeException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ServerResponse.createByErrorCodeMessage(e.getCode(), e.getMessage());
        }

        return ServerResponse.createBySuccess();
    }

    @DeleteMapping("/{id}")
    public ServerResponse delete(@PathVariable("id") Integer id, @RequestBody BlogTypeDTO data
            , HttpSession session , HttpServletResponse response){

        //判断用户是否正确
        if (!ValidUtil.userValid(response,session,data.getUserId()))
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.USERID_ERROR.getCode(),
                    SessionErrorEmun.USERID_ERROR.getMsg());

        data.setId(id);

        //非空校验
        if (null == data.getId() || null == data.getUserId())
        {
            log.error("insert:分类信息缺失");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.TYPE_INFO_EMPTY.getCode(),
                    SessionErrorEmun.TYPE_INFO_EMPTY.getMsg());
        }

        //操作service删除
        try {
            blogTypeService.delete(data);
        } catch (TypeException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ServerResponse.createByErrorCodeMessage(e.getCode(), e.getMessage());
        }

        return ServerResponse.createBySuccess();
    }
}
