package com.mola.molablogv2.viewobj;

import com.mola.molablogv2.dto.client.ClientDTO;
import lombok.Data;

import java.util.List;

/**
 * @Author: molamola
 * @Date: 19-7-22 上午10:10
 * @Version 1.0
 * 客户端搜索VO
 */
@Data
public class SearchVO {

    private Integer code;

    private String keyword;

    private String msg;

    private List<ClientDTO> data;

    public SearchVO(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public SearchVO(){
    }
}
