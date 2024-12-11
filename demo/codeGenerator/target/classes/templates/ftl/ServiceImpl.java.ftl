package ${config.packageName}.${config.serverPath}<#if config.isInterface==true>.impl</#if>;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import com.github.yulichang.base.MPJBaseServiceImpl;
import java.util.List;
import com.sun.demo.system.common.utils.PageUtils;
import ${config.packageName}.${config.entityPath}.${modelName}${config.entitySuffix};
import ${config.packageName}.mapper.${modelName}Mapper;
<#if config.isInterface==true>import ${config.packageName}.${config.serverPath}.${modelName}${config.serverSuffix};</#if>

/**
 *  <#if remark ??>${remark}</#if>
 *  @author:<#if config.author ??>${config.author}</#if>
 */
@Service
public class ${modelName}${config.serverSuffix}<#if config.isInterface==true>Impl</#if> extends MPJBaseServiceImpl<${modelName}Mapper,${modelName}${config.entitySuffix}> <#if config.isInterface==true>implements ${modelName}${config.serverSuffix}</#if> {
   public IPage<${modelName}${config.entitySuffix}> selectPage(${modelName}${config.entitySuffix} filter){
       IPage<${modelName}${config.entitySuffix}> page=PageUtils.getPage();
       List<${modelName}${config.entitySuffix}> list = baseMapper.selectPage(page, filter);
       page.setRecords(list);
       return page;
   }
}