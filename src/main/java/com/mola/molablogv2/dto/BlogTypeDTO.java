package com.mola.molablogv2.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-6-27 下午2:40
 * @Version 1.0
 * BlogType传输对象
 */
@Data
public class BlogTypeDTO {

    /**
     * type的id
     */
    private Integer id;

    /**
     * type的名称
     */
    private String className;

    /**
     * type的父id
     */
    private Integer parentId;

    /**
     * type的userId
     */
    private Integer userId;

    /**
     * type的子节点
     */
    private List<BlogTypeDTO> children;
}
