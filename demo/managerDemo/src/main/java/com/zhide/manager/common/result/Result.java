package com.zhide.manager.common.result;

public class Result <T> extends ResultBase{
    private T data;

    public Result() {
    }

    public T getData() {return data;}

    public void setData(T data) {this.data = data;}

}
