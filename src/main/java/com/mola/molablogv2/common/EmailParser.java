package com.mola.molablogv2.common;

import lombok.Data;

/**
 * @Author: molamola
 * @Date: 19-7-31 上午10:12
 * @Version 1.0
 * 负责传输邮件内容
 */
@Data
public class EmailParser {

    /**
     * 标题
     */
    private String subject;

    /**
     * 正文
     */
    private String content;
}
