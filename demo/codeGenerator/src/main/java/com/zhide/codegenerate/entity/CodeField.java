package com.zhide.codegenerate.entity;

import com.zhide.codegenerate.common.TableToCodeUtils;
import lombok.Data;

@Data
public class CodeField {
    /**
     * 模型名称
     */
    private String fieldName;
    /**
     * 字段名称
     */
    private String tableFieldName;
    /**
     * 实体中数据类型
     */
    private String dataType;
    /**
     * 数据库中数据类型
     */
    private String dbType;
    /**
     *  true 是主键
     */
    private boolean isKey=false;
    /**
     * 字段描述
     */
    private String comment;

    /**
     * 首字母大写的字段名
     * @return
     */
    public String getFirstUpFieldName(){
        return TableToCodeUtils.firstUp(fieldName);
    }
}
