package com.zhide.codegenerate.common;

import com.zhide.codegenerate.entity.FieldEntity;
import com.zhide.codegenerate.entity.TableEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtils {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static String url;
    private static String user;
    private static String pwd;
    private static String dbname;
    /**
     * 获取connection连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        String a="jdbc:mysql://"+url+"/"+dbname+"?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=Asia/Shanghai";
        return DriverManager.getConnection(a, user, pwd);
    }
    public static void setConfig(Config config){
        JdbcUtils.url= config.getUrl();
        JdbcUtils.user= config.getUname();
        JdbcUtils.pwd= config.getPwd();
        JdbcUtils.dbname= config.getDbName();
    }
    /**
     * 释放st,conn资源
     *
     * @param statement
     * @param connection
     */
    public static void close(Statement statement, Connection connection) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs, Statement stmt,Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取数据库的所有表
     * @return
     */
    public static List<TableEntity> getAllTable() throws SQLException {
        String sql="SELECT table_name,update_time,table_comment FROM information_schema.tables WHERE table_schema = '"+dbname+"' ORDER BY UPDATE_TIME desc";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<TableEntity> list = new ArrayList<TableEntity>();
        try {
            conn = getConnection();
        } catch (SQLException e) {
            throw e;
        }
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        //6、遍历结果集、封装对象装载集合
        TableEntity emp = null;
        while(rs.next()){
            String name = rs.getString("table_name");
            Timestamp updateTime = rs.getTimestamp("update_time");
            String comment = rs.getString("table_comment");
            LocalDateTime localDateTime = null;
            if(updateTime!=null)
                localDateTime=LocalDateTime.ofEpochSecond(updateTime.getTime()/1000,0, ZoneOffset.ofHours(8));
            emp = new TableEntity(name,localDateTime,comment);
            list.add(emp);
        }
        close(rs, stmt, conn);
        return list;
    }

    /**
     * 获取表的所有字段
     * @param tableName 表名
     * @return
     */
    public static List<FieldEntity> getTableField(String tableName){
        String sql="SELECT column_name,data_type,column_key,column_comment FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='"+dbname+"' and table_name='"+tableName+"'";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<FieldEntity> list = new ArrayList<FieldEntity>();
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            //6、遍历结果集、封装对象装载集合
            FieldEntity emp = null;
            while(rs.next()){
                String columnName = rs.getString("COLUMN_NAME");
                String type = rs.getString("DATA_TYPE");
                String key = rs.getString("COLUMN_KEY");
                String comment = rs.getString("COLUMN_COMMENT");
                emp = new FieldEntity(columnName,type,key,comment);
                list.add(emp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            close(rs, stmt, conn);
        }
        return list;
    }
}
