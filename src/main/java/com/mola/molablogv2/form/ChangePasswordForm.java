package com.mola.molablogv2.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author : molamola
 * @Project: molablogv2
 * @Description:
 * @date : 2021-04-21 13:08
 **/
@Data
public class ChangePasswordForm {

    /**
     * 原密码
     */
    @NotEmpty(message = "原密码必填")
    private String passwordPre;

    /**
     * 现密码
     */
    @NotEmpty(message = "现密码必填")
    private String passwordNow;
}
