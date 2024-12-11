package com.zhide.codegenerate.entity;

import com.zhide.codegenerate.common.Config;
import lombok.Data;

import java.util.List;

@Data
public class CodeTable {
    private String tableName; //表名

    private String modelName; //实体名

    private String remark; //表描述

    private List<CodeField> list; //字段

    private Config config;//配置信息
}
