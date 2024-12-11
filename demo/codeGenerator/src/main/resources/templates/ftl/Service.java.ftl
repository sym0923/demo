package ${config.packageName}.${config.serverPath};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${config.packageName}.${config.entityPath}.${modelName}${config.entitySuffix};
import com.github.yulichang.base.MPJBaseService;

/**
 *  <#if remark ??>${remark}</#if>
 *  @author:<#if config.author ??>${config.author}</#if>
 */
public interface ${modelName}${config.serverSuffix} extends MPJBaseService<${modelName}${config.entitySuffix}>{
    public IPage<${modelName}${config.entitySuffix}> selectPage(${modelName}${config.entitySuffix} filter);
}