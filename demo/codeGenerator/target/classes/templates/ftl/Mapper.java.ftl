package ${config.packageName}.mapper;

import ${config.packageName}.${config.entityPath}.${modelName}${config.entitySuffix};
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *  <#if remark ??>${remark}</#if>
 *  @author:<#if config.author ??>${config.author}</#if>
 */
@Mapper
public interface ${modelName}Mapper extends MPJBaseMapper<${modelName}${config.entitySuffix}> {

    public List<${modelName}${config.entitySuffix}> selectPage(@Param("page") IPage page,@Param("entity") ${modelName}${config.entitySuffix} entity);

}