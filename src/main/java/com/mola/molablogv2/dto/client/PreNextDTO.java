package com.mola.molablogv2.dto.client;

import lombok.Data;

/**
 * @Author: molamola
 * @Date: 19-7-18 下午5:13
 * @Version 1.0
 */
@Data
public class PreNextDTO {

    /**
     * 下一篇的id
     */
    private Integer nextId;

    /**
     * 下一篇的题目
     */
    private String nextTitle;

    /**
     * 上一篇的id
     */
    private Integer preId;

    /**
     * 上一篇的题目
     */
    private String preTitle;
}
