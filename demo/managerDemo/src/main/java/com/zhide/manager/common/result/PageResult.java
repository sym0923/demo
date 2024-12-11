package com.zhide.manager.common.result;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResult <T> extends ResultBase{
    private Long total;  // 总记录数
    private List<T> data;  // 当前页数据
    private Long pageNum;  // 当前页码
    private Long pageSize;  // 每页数量
    private Long totalPage;  // 总页数
}
