package com.zhide.manager.common.result;

import lombok.Getter;

@Getter
public enum ResultEnum {
    ok("200","成功"),
    error("500","失败"),
    bad_request("400","请求参数错误"),
    not_found("404","找不到资源"),
    unauthorized("401","未授权"),
    forbidden("403","禁止访问"),
    systemError("500","系统错误");


    private String msg;
    private String code;

    private ResultEnum(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
    @Override
    public String toString(){
        return code+","+msg;
    }
}
