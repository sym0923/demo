package com.zhide.codegenerate.controller;

import com.zhide.codegenerate.common.Config;
import com.zhide.codegenerate.common.JdbcUtils;
import com.zhide.codegenerate.common.Result;
import com.zhide.codegenerate.entity.TableEntity;
import com.zhide.codegenerate.server.GenerateServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/api")
public class Generate {
    @Autowired
    private GenerateServer generateServer;

    @PostMapping("/tables")
    @ResponseBody
    public Result tables(@RequestBody Config config){
        JdbcUtils.setConfig(config);
        try{
            List<TableEntity> allTable = JdbcUtils.getAllTable();
            Result result=new Result();
            result.setData(allTable);
            return result;
        }catch (SQLException e){
            String err=e.getMessage();
            if(err.indexOf("Unknown database")>-1){
                Result result=new Result();
                result.setCode("-3");
                result.setMsg("没找到数据库");
                return result;
            } if(err.indexOf("Communications link failure")>-1){
                Result result=new Result();
                result.setCode("-2");
                result.setMsg("连接异常。");
                return result;
            }else{
                Result result=new Result();
                result.setCode("-4");
                result.setMsg("系统异常。");
                return result;
            }

        }catch (Exception e){
            Result result=new Result();
            result.setCode("-2");
            result.setMsg("系统异常");
            return result;
        }
    }
//    @PostMapping("/field")
//    @ResponseBody
//    public List<FieldEntity> tables(@RequestBody Config config, String tableName){
//        JdbcUtils.setConfig(config);
//        List<FieldEntity> allTable = JdbcUtils.getTableField(tableName);
//        return allTable;
//    }
    @PostMapping("/generate")
    @ResponseBody
    public void generate(@RequestBody Config config) {
        JdbcUtils.setConfig(config);
        generateServer.generateCode(config);
    }
    @PostMapping("/generateOne")
    @ResponseBody
    public void generateOne(@RequestBody Config config){
        JdbcUtils.setConfig(config);
        generateServer.generateOne(config);
    }
}
