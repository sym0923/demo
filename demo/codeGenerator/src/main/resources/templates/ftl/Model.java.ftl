package ${config.packageName}.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
<#if config.isdata??>import lombok.Data;</#if>

/**
 *  <#if remark ??>${remark}</#if>
 *  @author:<#if config.author ??>${config.author}</#if>
 */
<#if config.isdata==true>@Data</#if>
@TableName("${tableName}")
public class ${modelName}${config.entitySuffix}{

<#if list??>
    <#list list as column>
    /**
    *  <#if column.comment??>${column.comment}</#if>
    */
    <#if column.dataType=="LocalDateTime">@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")</#if>
    @TableField("${column.tableFieldName}")
    private ${column.dataType} ${column.fieldName};
    </#list>
</#if>
<#if config.isdata==false && list??>
    <#list list as column>

    public ${column.dataType} get${column.firstUpFieldName}(){
        return ${column.fieldName};
    }
    public void set${column.firstUpFieldName}(${column.dataType} ${column.fieldName}){
        this.${column.fieldName}=${column.fieldName};
    }
    </#list>
</#if>

    public MPJLambdaWrapper<${modelName}${config.entitySuffix}> toWarpper(){
        MPJLambdaWrapper<${modelName}${config.entitySuffix}> lambda = JoinWrappers.lambda(${modelName}${config.entitySuffix}.class);
    <#list list as column>
        <#if column.dataType=="String">
        lambda.eq(${column.fieldName}!=null && !${column.fieldName}.trim().isEmpty(),${modelName}${config.entitySuffix}::get${column.firstUpFieldName},${column.fieldName});
        <#else>
        lambda.eq(${column.fieldName}!=null,${modelName}${config.entitySuffix}::get${column.firstUpFieldName},${column.fieldName});
        </#if>
    </#list>
        return lambda;
    }
}