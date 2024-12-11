package com.zhide.codegenerate.entity;

import lombok.Data;

@Data
public class FieldEntity {
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     *  字段索引类型，PRI主键  MUL普通索引  UNI唯一索引
     */
    private String columnKey;
    /**
     * 字段描述
     */
    private String comment;

    public FieldEntity(){}
    public FieldEntity(String fieldName,String dataType,String columnKey,String comment){
        this.fieldName=fieldName;
        this.dataType=dataType;
        this.columnKey=columnKey;
        this.comment=comment;
    }
}
