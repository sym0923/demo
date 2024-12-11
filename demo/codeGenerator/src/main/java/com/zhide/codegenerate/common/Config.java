package com.zhide.codegenerate.common;

import com.zhide.codegenerate.entity.TableEntity;
import lombok.Data;

import java.util.List;

@Data
public class Config {
    private String url; //数据库地址
    private String uname; //数据库用户名
    private String pwd;//数据库密码
    private String dbName;//数据库名
    private String realPath="/";//保存地址
    private Boolean isdata = true;//是否用data注解
    private Boolean isInterface=false;//是否生成服务层接口
    private String author=""; //作者
    private String packageName="com.demo";//包
    private Boolean isLocal;//false下载/true本地

    private String entitySuffix="entity";//实体后缀
    private String serverSuffix="service";//服务层后缀
    private List<TableEntity> tables;//要生成的表
    private Integer generateOne;//单个生成 0其他，1实体，2接口，3服务，4Mapper，5xml，6Controller

    public String getServerPath(){
        String path = serverSuffix.trim();//处理实体后缀
        if(path==null || path.length()==0){
            return "service";
        }else{
            return serverSuffix.toLowerCase();
        }
    }
    public String getEntityPath(){
        String path = entitySuffix.trim();//处理实体后缀
        if(path==null || path.length()==0){
             return "entity";
        }else{
            return entitySuffix.toLowerCase();
        }
    }
}
