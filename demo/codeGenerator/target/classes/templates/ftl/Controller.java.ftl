package ${config.packageName}.controller;

import ${config.packageName}.${config.entityPath}.${modelName}${config.entitySuffix};
import ${config.packageName}.${config.serverPath}.${modelName}${config.serverSuffix};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.demo.common.result.Result;
import com.sun.demo.common.result.ResultEnum;
import com.sun.demo.common.result.ResultPage;
import com.sun.demo.webapi.common.BaseController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/${modelName}")
public class ${modelName}Controller extends BaseController<${modelName}${config.entitySuffix}>{

    @Autowired
    private ${modelName}${config.serverSuffix} ${config.serverPath};

    @GetMapping(value="/detail")
    public Result<${modelName}${config.entitySuffix}> detail(String id){
        ${modelName}${config.entitySuffix} entity = server.getById(id);
        return success(entity);
    }
    @PostMapping(value = "/page")
    public ResultPage<${modelName}${config.entitySuffix}> list(@RequestBody ${modelName}${config.entitySuffix} entity){
        IPage<${modelName}${config.entitySuffix}> page = ${config.serverPath}.selectPage(entity);
        return getResultPage(page);
    }
    @PostMapping(value="/save")
    public Result save(@RequestBody ${modelName}${config.entitySuffix} entity) {
        boolean save = server.save(entity);
        if(save){
            return success();
        }else{
            return error(ResultEnum.runErr);
        }
    }
    @PostMapping(value="/update")
    public Result update(@RequestBody ${modelName}${config.entitySuffix} entity) {
        boolean b = server.updateById(entity);
        if(b){
            return success();
        }else{
            return error(ResultEnum.runErr);
        }
    }
    @GetMapping(value="/delete")
    public Result deleteByPk(String id){
        boolean b = server.removeById(id);
        if(b){
            return success();
        }else{
            return error(ResultEnum.runErr);
        }
    }
}