package com.zhide.codegenerate.common;

import com.zhide.codegenerate.entity.CodeField;
import com.zhide.codegenerate.entity.CodeTable;
import com.zhide.codegenerate.entity.FieldEntity;
import com.zhide.codegenerate.entity.TableEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableToCodeUtils {

    private static final Pattern linePattern = Pattern.compile("_(\\w)");

    public static List<CodeTable> tableToCode(List<TableEntity> list){
        List<CodeTable> result=new ArrayList<>(list.size());
        for (TableEntity tableEntity : list) {
            String tbname=tableEntity.getTableName().trim();
            CodeTable table=new CodeTable();
            table.setRemark(tableEntity.getRemark());
            table.setModelName(tableToHump(tbname));
            table.setTableName(tbname);
            result.add(table);
        }
        return result;
    }
    public static List<CodeField> fieldToCode(List<FieldEntity> list){
        List<CodeField> result=new ArrayList<>(list.size());
        boolean iskey=false;
        for (FieldEntity fieldEntity : list) {
            if("PRI".equalsIgnoreCase(fieldEntity.getColumnKey())){
                iskey=true;
            }else{
                iskey=false;
            }
            CodeField field=new CodeField();
            field.setComment(fieldEntity.getComment());
            field.setTableFieldName(fieldEntity.getFieldName());
            field.setFieldName(fieldToHump(fieldEntity.getFieldName()));
            field.setKey(iskey);
            field.setDataType(getFieldType(fieldEntity.getDataType()));
            field.setDbType(fieldEntity.getDataType());
            result.add(field);
        }
        return result;
    }
    private static String getFieldType(String field){
        field=field.toUpperCase().trim();
        switch (field){
            case "LONGTEXT":
            case "TEXT":
            case "VARCHAR":
                return "String";
            case "TINYINT":
                return "byte";
            case "INT":
            case "YEAR":
                return "Integer";
            case "BIGINT":
                return "Long";
            case "DATETIME":
            case "TIMESTAMP":
                return "LocalDateTime";
            case "FLOAT":
                return "Float";
            case "DOUBLE":
                return "Double";
            case "BIT":
                return "Boolean";
            case "DECIMAL":
                return "BigDecimal";
            case "BLOB":
                return "byte[]";
        }
        return "不认识的类型";
    }

    /*表名转换*/
    public static String tableToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        char c = sb.charAt(0);
        if(c>='a' && c<='z'){
            c = (char) ((int) c - 32);
            sb.setCharAt(0,c);
        }
        return sb.toString();
    }
    /*首字母大写*/
    public static String firstUp(String str) {
        StringBuffer sb = new StringBuffer(str);
        char c = sb.charAt(0);
        if(c>='a' && c<='z'){
            c = (char) ((int) c - 32);
            sb.setCharAt(0,c);
        }
        return sb.toString();
    }
    /*字段名转换*/
    public static String fieldToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
