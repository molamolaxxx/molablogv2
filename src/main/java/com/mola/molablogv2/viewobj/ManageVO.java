package com.mola.molablogv2.viewobj;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-6-29 下午4:33
 * @Version 1.0
 * 博客前端显示内容
 */
@Data
public class ManageVO<T> {

    /**
     * 错误码
     */
    @JsonProperty("code")
    private Integer code;

    /**
     * 总共有多少条数据
     */
    @JsonProperty("count")
    private Integer count;

    /**
     * 返回消息
     */
    @JsonProperty("msg")
    private String msg;

    /**
     * data
     */
    @JsonProperty("data")
    private List<T> data;

    public ManageVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ManageVO() {
    }
}
