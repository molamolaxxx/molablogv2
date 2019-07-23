package com.mola.molablogv2.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-6-29 下午4:20
 * @Version 1.0
 * 管理端manage页面 传输对象
 */
@Data
public class ManageDTO<T> {

    /**
     * 总数
     */
    private Integer totalCount;

    /**
     * 数据
     */
    private List<T> data;

}
