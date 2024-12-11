package com.zhide.codegenerate.common;

import lombok.Data;

@Data
public class Result {
    private String code="0";
    private String msg="成功";
    private Object data;
}
