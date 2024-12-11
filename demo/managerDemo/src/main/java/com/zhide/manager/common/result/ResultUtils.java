package com.zhide.manager.common.result;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public class ResultUtils {
    /**
     * 返回成功，通常带有泛型的实体类
     * @param data 要返回的实体类
     * @return Result
     * @param <T> 泛型
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        if (data != null && data instanceof ResultEnum){
            ResultEnum rdata = (ResultEnum) data;
            result.setCode(rdata.getCode());
            result.setMessage(rdata.getMsg());
        }else {
            result.setCode(ResultEnum.ok.getCode());
            result.setMessage(ResultEnum.ok.getMsg());
            result.setData(data);
        }
        return result;
    }

    /**
     * 返回成功，不带泛型,通常就是code:200,msg：成功
     * @return Result
     * @param <T> 泛型
     */
    public static <T> Result<T> success(){
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.ok.getCode());
        result.setMessage(ResultEnum.ok.getMsg());
        return result;
    }

    /**
     * 返回分页数据
     * @param data 列表数据
     * @param total 列表总数
     * @param pageNum 当前页码
     * @param pageSize 当前页数量
     * @param totalPage 总页码
     * @return 分页对象
     * @param <T> 泛型
     */
    public static <T> PageResult<T> pageSuccess(List<T> data,Long total,Long pageNum,Long pageSize,Long totalPage){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCode(ResultEnum.ok.getCode());
        pageResult.setMessage(ResultEnum.ok.getMsg());
        pageResult.setData(data);
        pageResult.setTotal(total);
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalPage(totalPage);
        return pageResult;
    }

    /**
     * 创建一个成功分页的PageResult对象
     * 该方法用于将MyBatis-Plus的IPage对象转换为自定义的PageResult对象，主要用于返回给前端
     * 它封装了分页信息和数据，以及成功状态码和消息
     *
     * @param page MyBatis-Plus的IPage对象，包含分页数据和分页信息
     * @return 返回一个包含分页信息和数据的PageResult对象
     * @param <T> 泛型参数，表示页面数据的类型
     */
    public static <T> PageResult<T> pageSuccess(IPage<T> page){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCode(ResultEnum.ok.getCode());
        pageResult.setMessage(ResultEnum.ok.getMsg());
        pageResult.setTotal(page.getTotal());
        pageResult.setPageNum(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setTotalPage(page.getPages());
        pageResult.setData(page.getRecords());
        return pageResult;
    }

    /**
     * 返回自定义的code，msg的错误信息
     * @param code 错误码
     * @param msg 自定义错误信息
     * @return Result
     * @param <T> 泛型
     */
    public static <T> Result<T> error(String code,String msg){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    /**
     * 返回Enum中预定义好的错误信息
     * @param resultEnum 错误信息
     * @return Result
     * @param <T> 泛型
     */
    public static <T> Result<T> error(ResultEnum resultEnum){
        Result<T> result = new Result<>();
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMsg());
        return result;
    }
}
