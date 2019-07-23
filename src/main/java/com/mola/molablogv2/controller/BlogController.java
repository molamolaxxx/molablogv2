package com.mola.molablogv2.controller;

import com.mola.molablogv2.common.ResponseCode;
import com.mola.molablogv2.common.ServerResponse;
import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.dto.BlogDTO;
import com.mola.molablogv2.emun.BlogErrorEmun;
import com.mola.molablogv2.emun.SessionErrorEmun;
import com.mola.molablogv2.exception.BlogException;
import com.mola.molablogv2.service.BlogService;
import com.mola.molablogv2.utils.ValidUtil;
import com.mola.molablogv2.viewobj.ManageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: molamola
 * @Date: 19-6-28 上午9:21
 * @Version 1.0
 * 关于博客api的controller
 */
@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogController {

    @Autowired
    private BlogService blogService;
    /**
     * 博客管理页面：获取所有的博客信息
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping
    private ManageVO selectAll(@RequestParam("userId") Integer userId,
                               @RequestParam("offset") Integer offset,
                               @RequestParam("limit") Integer limit, HttpServletResponse response , HttpSession session){

        //判断用户是否正确
        if (!ValidUtil.userValid(response,session,userId))
            return new ManageVO(SessionErrorEmun.USERID_ERROR.getCode(),
                    SessionErrorEmun.USERID_ERROR.getMsg());

        ManageDTO<BlogDTO> manageDTO;
        ManageVO<BlogDTO> result = new ManageVO();

        try {
            manageDTO = blogService.selectAll(userId,offset,limit);
        }
        catch (Exception e) {
            result.setMsg(BlogErrorEmun.BLOG_SELECT_ALL_ERROR.getMsg());
            result.setCode(BlogErrorEmun.BLOG_SELECT_ALL_ERROR.getCode());
            return result;
        }

        result.setCount(manageDTO.getTotalCount());
        result.setData(manageDTO.getData());

        result.setCode(ResponseCode.SUCCESS.getCode());
        result.setMsg(ResponseCode.SUCCESS.getDesc());

        return result;
    }

    @GetMapping("/content")
    private ServerResponse content(@RequestParam("blogId") Integer blogId){
        String content = null;
        try {
            content = blogService.selectContent(blogId);
        } catch (BlogException e) {
            return ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        Map rsMap = new HashMap();
        rsMap.put("content",content);
        return ServerResponse.createBySuccess(rsMap);
    }

    @PostMapping
    private ServerResponse insert(@RequestBody BlogDTO data, HttpServletResponse response, HttpSession session){
        //判断用户是否正确
        if (!ValidUtil.userValid(response,session,data.getUserId()))
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.USERID_ERROR.getCode(),
                    SessionErrorEmun.USERID_ERROR.getMsg());

        //操作service插入
        try {
            blogService.insert(data);
        } catch (BlogException e) {
            return ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }
        return ServerResponse.createBySuccess();
    }

    @PutMapping("/{blogId}")
    private ServerResponse update(@PathVariable("blogId") Integer blogId ,
                                  @RequestBody BlogDTO data , HttpServletResponse response, HttpSession session){

        //判断用户是否正确
        if (!ValidUtil.userValid(response,session,data.getUserId()))
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.USERID_ERROR.getCode(),
                    SessionErrorEmun.USERID_ERROR.getMsg());

        //1.给dto设置id
        data.setId(blogId);

        //2. 操作service更新
        try {
            blogService.update(data);
        }
        catch (BlogException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        return ServerResponse.createBySuccess();
    }

    @DeleteMapping("/{blogId}")
    private ServerResponse delete(@PathVariable("blogId") Integer blogId ,
                                  @RequestBody BlogDTO data , HttpServletResponse response, HttpSession session){
        //判断用户是否正确
        if (!ValidUtil.userValid(response,session,data.getUserId()))
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.USERID_ERROR.getCode(),
                    SessionErrorEmun.USERID_ERROR.getMsg());

        data.setId(blogId);

        //1.判断用户是否正确
        if (null == session.getAttribute("userId") || session.getAttribute("userId") != data.getUserId()) {
            log.error("deleteBlog:用户信息不正确");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ServerResponse.createByErrorCodeMessage(SessionErrorEmun.USERID_ERROR.getCode(),
                    SessionErrorEmun.USERID_ERROR.getMsg());
        }

        //2. 操作service删除
        try {
            blogService.delete(data);
        }
        catch (BlogException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        return ServerResponse.createBySuccess();
    }

    @GetMapping("/search")
    private ManageVO search(@RequestParam("keyword") String keyword,
                            @RequestParam("offset") Integer offset,
                            @RequestParam("limit") Integer limit,HttpSession session){
        //session中查找userId
        Integer userId = null;
        if (null != session.getAttribute("userId")){
            userId = (Integer) session.getAttribute("userId");
        }
        else {
            return new ManageVO(SessionErrorEmun.USERID_ERROR.getCode(),
                    SessionErrorEmun.USERID_ERROR.getMsg());
        }

        //调用service
        ManageDTO dto = null;
        try {
            dto = blogService.search(keyword,userId);
        } catch (BlogException e) {
            return new ManageVO(e.getCode(), e.getMessage());
        }

        //vo
        ManageVO vo = new ManageVO();
        vo.setCode(ResponseCode.SUCCESS.getCode());
        vo.setMsg(ResponseCode.SUCCESS.getDesc());
        vo.setCount(dto.getTotalCount());
        vo.setData(dto.getData());

        return vo;
    }
}
