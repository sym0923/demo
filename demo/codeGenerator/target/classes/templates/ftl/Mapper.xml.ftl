<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${config.packageName}.mapper.${modelName}Mapper">
    <!-- 默认没用，备用 -->
    <resultMap id="BaseResultMap" type="${config.packageName}.${config.entityPath}.${modelName}${config.entitySuffix}">
        <#list list as column>
            <result column="${column.tableFieldName}" property="${column.fieldName}"/>
        </#list>
    </resultMap>
    <!-- 默认没用，备用 -->
    <sql id="selectVo">
        <#list list as column>`${column.tableFieldName}`<#if column?is_last=false>,</#if></#list>
    </sql>

    <!-- 分页查询 -->
    <select id="selectPage" parameterType="${config.packageName}.${config.entityPath}.${modelName}${config.entitySuffix}"  resultType="${config.packageName}.${config.entityPath}.${modelName}${config.entitySuffix}">
        select * from ${tableName}
        <where>
            <#list list as column>
                <if test="entity.${column.fieldName} != null"> and ${column.tableFieldName} = ${r"#{entity."}${column.fieldName}${r"}"}</if>
            </#list>
        </where>
        order by ${list?first.fieldName} desc
    </select>
${r"<!-- like查询  field like concat('%', #{fileName}, '%') -->"}
</mapper>