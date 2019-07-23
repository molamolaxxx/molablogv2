package com.mola.molablogv2.controller.client;

import com.mola.molablogv2.common.ResponseCode;
import com.mola.molablogv2.common.ServerResponse;
import com.mola.molablogv2.dto.CommentDTO;
import com.mola.molablogv2.dto.ManageDTO;
import com.mola.molablogv2.dto.client.ClientDTO;
import com.mola.molablogv2.dto.client.PreNextDTO;
import com.mola.molablogv2.emun.ClientErrorEmun;
import com.mola.molablogv2.emun.UserErrorEmun;
import com.mola.molablogv2.exception.ClientException;
import com.mola.molablogv2.form.CommentForm;
import com.mola.molablogv2.service.client.PageService;
import com.mola.molablogv2.utils.IpUtils;
import com.mola.molablogv2.utils.TokenUtils;
import com.mola.molablogv2.viewobj.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;

/**
 * @Author: molamola
 * @Date: 19-7-15 上午11:03
 * @Version 1.0
 * blog.html页面controller
 */
@Controller
@RequestMapping("/page")
@Slf4j
public class PageController {

    @Autowired
    private PageService pageService;

    @GetMapping("/{id}")
    private String blog(@PathVariable("id") Integer id , Model model , HttpServletResponse response){
        //1.查找
        ClientDTO result = null;
        try {
            result = pageService.selectOne(id);
        } catch (ClientException e) {
            log.error("{},{}",e.getCode(),e.getMessage());
            if (e.getCode() == ClientErrorEmun.NO_BLOG_EXIST.getCode()){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            return null;
        }

        //2.处理日期
        String createTime = new SimpleDateFormat("yyyy-MM-dd")
                .format(result.getCreateTime());

        //3.放入model
        model.addAttribute("content",result.getContent());
        model.addAttribute("createTime",createTime);
        model.addAttribute("className",result.getClassName());
        model.addAttribute("title",result.getTitle());

        return "blog";
    }

    @GetMapping("/preNext")
    @ResponseBody
    private ServerResponse preNext(@RequestParam("blogId") Integer blogId){

        PreNextDTO dto = null;
        try {
            dto = pageService.preNext(blogId);
        } catch (ClientException e) {
            return ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        return ServerResponse.createBySuccess(dto);
    }

    //todo 插入评论
    @PostMapping("/comment")
    @ResponseBody
    private ServerResponse insertComment(HttpServletRequest request, @Valid CommentForm form,
                                         BindingResult bindingResult, HttpServletResponse response){

        //1.表单判断
        if (bindingResult.hasErrors()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ServerResponse.createByErrorCodeMessage(UserErrorEmun.FORM_VALID_FAILED.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }

        //2.验证token，为空或错误返回403
        String userAgent = request.getHeader("User-Agent");

        if (null == form.getToken() || !TokenUtils.getToken(userAgent).equalsIgnoreCase(form.getToken())){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return ServerResponse.createByErrorCodeMessage(ClientErrorEmun.TOKEN_ERROR.getCode()
                    ,ClientErrorEmun.TOKEN_ERROR.getMsg());
        }

        //3.拼接,插入评论
        CommentDTO dto = new CommentDTO();
        BeanUtils.copyProperties(form,dto);
        dto.setIp(IpUtils.getIp(request));
        try {
            pageService.insertComment(dto);
        } catch (ClientException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return ServerResponse.createByErrorCodeMessage(e.getCode(),e.getMessage());
        }

        return ServerResponse.createBySuccess();
    }


    @GetMapping("/comment")
    @ResponseBody
    private CommentVO listComment(@RequestParam("blogId") Integer blogId,
                                  @RequestParam("offset") Integer offset,
                                  @RequestParam("limit") Integer limit){

        ManageDTO manageDTO = null;
        try {
            manageDTO = pageService.listComment(blogId,offset,limit);
        } catch (ClientException e) {
            return new CommentVO(e.getCode(),e.getMessage());
        }

        CommentVO commentVO = new CommentVO();
        commentVO.setCode(ResponseCode.SUCCESS.getCode());
        commentVO.setMsg(ResponseCode.SUCCESS.getDesc());
        commentVO.setCount(manageDTO.getTotalCount());
        commentVO.setData(manageDTO.getData());

        return commentVO;
    }
}
