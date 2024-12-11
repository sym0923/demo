package com.zhide.codegenerate.entity;

import java.time.LocalDateTime;

/**
 *  前端用户表
 *  @author:123
 */

public class sysUsers{

    /**
    *  
    */
    private String id;
    /**
    *  用户名
    */
    private String loginName;
    /**
    *  密码
    */
    private String pwd;
    /**
    *  电话
    */
    private String tel;
    /**
    *  邮箱
    */
    private String email;
    /**
    *  用户状态，0正常 1锁定
    */
    private Integer status;
    /**
    *  创建时间
    */
    private LocalDateTime createTime;

    public String getid(){
        return id;
    }
    public void setid(String id){
        this.id=id;
    }

    public String getloginName(){
        return loginName;
    }
    public void setloginName(String loginName){
        this.loginName=loginName;
    }

    public String getpwd(){
        return pwd;
    }
    public void setpwd(String pwd){
        this.pwd=pwd;
    }

    public String gettel(){
        return tel;
    }
    public void settel(String tel){
        this.tel=tel;
    }

    public String getemail(){
        return email;
    }
    public void setemail(String email){
        this.email=email;
    }

    public Integer getstatus(){
        return status;
    }
    public void setstatus(Integer status){
        this.status=status;
    }

    public LocalDateTime getcreateTime(){
        return createTime;
    }
    public void setcreateTime(LocalDateTime createTime){
        this.createTime=createTime;
    }
}