package com.mola.molablogv2.viewobj;

import com.mola.molablogv2.dto.CommentDTO;
import lombok.Data;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-22 下午12:05
 * @Version 1.0
 * 客户端返回前端时评论的VO
 */
@Data
public class CommentVO {

    private Integer code;

    private Integer count;

    private String msg;

    private List<CommentDTO> data;

    public CommentVO(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public CommentVO(){
    }
}
