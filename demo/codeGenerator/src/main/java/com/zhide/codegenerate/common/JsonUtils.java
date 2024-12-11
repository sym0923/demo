package com.zhide.codegenerate.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
    static ObjectMapper mapper =null;
    static {
        mapper=new ObjectMapper();
    }
    public static String toStr(Object o){
        try{
            String json = mapper.writeValueAsString(o);
            return json;
        }catch (Exception  e){
            e.printStackTrace();
            return "";
        }
    }
    public static <T> T toObject(String json,Class<T> tClass){
        try{
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//反序列化的时候如果多了其他属性,不抛出异常
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);//如果是空对象的时候,不抛异常
            return mapper.readValue(json,tClass);
        }catch (Exception e){
            return null;
        }
    }
}
