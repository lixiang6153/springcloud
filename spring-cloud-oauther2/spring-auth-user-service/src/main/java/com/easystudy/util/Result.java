package com.easystudy.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data											// lombok自动生成getter、setter、构造、tostring等方法
@JsonInclude(JsonInclude.Include.NON_NULL)		// jackSon注解-注解不返回null值字段-如果值为null,则不返回
@SuppressWarnings("unused")
public class Result<T> implements Serializable {

    private static final String CODE = "code";
    private static final String MSG = "msg";
    private static final long serialVersionUID = 2633283546876721434L;

    private Integer code=200;
    private String description ="success";
    private T data;

    @JsonIgnore
    private HashMap<String,Object> exend;
    
    public Result() {
        exend = new HashMap<>();
    }

    @SuppressWarnings("rawtypes")
	public Result put(String key, Object value) {
        exend.put(key, value);
        return this;
    }
    
    @SuppressWarnings("rawtypes")
	public static Result failure(int code, String description) {
        Result result = new Result();
        result.setCode(code);
        result.setDescription(description);
        return result;
    }

    @SuppressWarnings("rawtypes")
	public static Result ok(String description) {
        Result result = new Result();
        result.put("description", description);
        return result;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Result ok(Map<String, Object> map) {
        Result result = new Result();
        result.exend.putAll(map);
        return result;
    }

    @SuppressWarnings("rawtypes")
	public static Result ok() {
        return new Result();
    }

}
