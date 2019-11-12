package com.fym.electrichousekeeper.common;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {

    public static final Integer NO_LOGIN = -1;

    public static final Integer OK = 1;

    public static final Integer ERROR = 2;

    public ApiResponse(Integer status){
        this.status = status;
    }

    /**
     * API访问状态
     */
    public Integer status;

    /**
     * 接口返回信息
     */
    public String message;

    /**
     * 接口返回数据列表
     */
    public List<?> rows;

    /**
     * 接口返回数据对象
     */
    public Object data;

    public static ApiResponse OK(){
        return new ApiResponse(OK);
    }
}
