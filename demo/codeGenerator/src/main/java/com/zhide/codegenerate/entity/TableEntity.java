package com.zhide.codegenerate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TableEntity {
    private String tableName; //表名
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime; //表最后修改时间
    private String remark; //表描述

    public TableEntity(){}
    public TableEntity(String tableName,LocalDateTime dataTime,String remark){
        this.tableName=tableName;
        this.dateTime=dataTime;
        this.remark=remark;
    }
}
