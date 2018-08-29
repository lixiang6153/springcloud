package com.easystudy.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.easystudy.annotation.ColumnDefinition;
import com.easystudy.annotation.JSONEnable;
import com.easystudy.error.ErrorCode;
import com.easystudy.error.ReturnValue;

/**
 * 用于获得JSON对象的类,实体类建议使用标准的javaBean
 * 
 * @author Administrator
 *
 */
public class JsonUtil {
	
	/**
	 * 获取Json字符串--不带返回值
	 * @param object
	 * @return
	 */
	public static String getJsonString(Object object){
		String json = "";
		// 集合类
		if(JsonUtil.isListOrSetOrMap(object)){
			json = handListOrSetOrMap(object).toString();	
		// 非集合类
		}else{
			// 值本来就是Json对象
			if(object instanceof JSONObject){
				json = object.toString();
			// 值返回是Json数组
			}else if(object instanceof JSONArray){
				json = object.toString();
			}else{
				JSONObject jsonObject = getJsonObject(object);
				json = jsonObject.toString();
			}
		}
		return json;
	}
	
	/**
	 * 获取Json字符串--不带返回值
	 * @param object
	 * @return
	 */
	public static String getJsonString(Map<String, Object> columns){
		String value = "";
		try {
			JSONObject jsonObject = new JSONObject();		
			Set<Map.Entry<String, Object>> entrys = columns.entrySet();
			for(Map.Entry<String, Object> e : entrys){
				// 基本类型
				if(JsonUtil.isBaseClass(e.getValue())){
					jsonObject.put(e.getKey(), e.getValue());
				// 集合类型
				}else if(JsonUtil.isListOrSetOrMap(e.getValue())){
					jsonObject.put(e.getKey(), JsonUtil.handListOrSetOrMap(e.getValue()));
				}else if(isBaseClass(e.getValue())){
					jsonObject.put("result", e.getValue());
				}else{
					JSONObject json = JsonUtil.getJsonObject(e.getValue());
					jsonObject.put(e.getKey(), json);
				}				
			}		
			value = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 返回标准的Json结果：
	 * {"error":0, "description":"success", result:5}
	 * {"error":0, "description":"success", result:{"name":"lixx", "age":"18", "sex":0, "department":"研发部"}}
	 * @param result
	 * @return
	 */
	public static String getJsonResult(ReturnValue result) {
		String value = "";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", result.getError());
			jsonObject.put("description", result.getDescription());	
			
			if(ErrorCode.ERROR_SUCCESS.getError() == result.getError() && result.getValue() != null){
				// 集合类
				if(JsonUtil.isListOrSetOrMap(result.getValue())){
					jsonObject.put("result", handListOrSetOrMap(result.getValue()));	
				// 非集合类
				}else{
					// 值本来就是Json对象
					if(result.getValue() instanceof JSONObject){
						jsonObject.put("result", result.getValue());		
					// 值返回是Json数组
					}else if(result.getValue() instanceof JSONArray){
						jsonObject.put("result", result.getValue());		
					}else if(isBaseClass(result.getValue())){
						jsonObject.put("result", result.getValue());	
					}else{
						JSONObject json = getJsonObject(result.getValue());
						jsonObject.put("result", json);		
					}
				}				
			}
			
			value = jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 返回带返回结果的json字符串
	 * @param columns
	 * @return
	 */
	public static String getJsonResult(Map<String, Object> columns){
		String value = "";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", ErrorCode.ERROR_SUCCESS.getError());
			jsonObject.put("description", ErrorCode.ERROR_SUCCESS.getDescription());
			
			Set<Map.Entry<String, Object>> entrys = columns.entrySet();
			for(Map.Entry<String, Object> e : entrys){
				// 基本类型
				if(JsonUtil.isBaseClass(e.getValue())){
					jsonObject.put(e.getKey(), e.getValue());
				// 集合类型
				}else if(JsonUtil.isListOrSetOrMap(e.getValue())){
					jsonObject.put(e.getKey(), JsonUtil.handListOrSetOrMap(e.getValue()));
				// 自定义对象
				}else{
					JSONObject json = JsonUtil.getJsonObject(e.getValue());
					jsonObject.put(e.getKey(), json);
				}
				
			}		
			value = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 返回带错误格式的json字符串
	 * @param json
	 * @return
	 */
	public static String getJsonResult(String json){
		String value = "";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", ErrorCode.ERROR_SUCCESS.getError());
			jsonObject.put("description", ErrorCode.ERROR_SUCCESS.getDescription());
			jsonObject.put("result", json);			
			value = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 将一个javaBean转化成JSONObject
	 * 
	 * @param o
	 *            需要转化的对象
	 * @return 返回javaBean转化成的JSONobject
	 */
	public static JSONObject getJsonObject(Object o) {
		JSONObject jsonObject = new JSONObject();
		try {
			Method[] methods = o.getClass().getMethods();
			for (Method method : methods) {
				JSONEnable jsonEnable = method.getAnnotation(JSONEnable.class);
				if (jsonEnable != null && jsonEnable.value().equals("false")) {// 过滤不需要转化的对象
					continue;
				}
				if (!method.getName().equals("getClass") && !method.getName().equals("getBoolean")) {
					method.setAccessible(true);
					if (method.getName().startsWith("get") || (method.getName().startsWith("is")
							&& (method.getReturnType() == Boolean.class || method.getReturnType() == boolean.class))) {
						Object value = method.invoke(o);
						String fieldName = getFieldNameByMethod(method);
						if (value != null) {
							if (isBaseClass(value)) {
								if (isDateClass(value)) {
									SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.YYYY_MM_DD_HH_MM_SS);
									value = dateFormat.format(value);
								}else if(value.getClass() == LocalTime.class){
									DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_TIME;
									value=format.format((TemporalAccessor) value);
								}else if(value.getClass() == LocalDate.class){
									DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE;
									value=format.format((TemporalAccessor) value);
								}else if(value.getClass() == LocalDateTime.class){
									DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
									value=format.format((TemporalAccessor) value);
								}
								jsonObject.put(fieldName, value);
							}else if (isListOrSetOrMap(value)){
								jsonObject.put(fieldName, handListOrSetOrMap(value));
							}else {
								jsonObject.put(fieldName, getJsonObject(value));// 注意不要转换成JSONArray,否则解析时出问题
							}
						} else {
							jsonObject.put(fieldName, "");
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * 将一个数组转化成JSONArray
	 * 
	 * @param objects
	 *            需要转化的数组
	 * @return 转化数组提到的JSONArray
	 */
	public static JSONArray getJsonArray(Object... objects) {
		JSONArray array = new JSONArray();
		for (Object object : objects) {
			array.put(getJsonObject(object));
		}
		return array;
	}
	/**
	 * 将json对象转换成javaBean
	 * 
	 * @param c
	 * @param jsonObject
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object convertTOJavaBean(Class c, JSONObject jsonObject) {
		List<Field> fields = getAllFields(c);
		try {
			Object instance = c.newInstance();
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				if (fieldName.equals("serialVersionUID")) {
					continue;
				}
				if (jsonObject.has(fieldName)) {
					Object value = jsonObject.get(fieldName);
					Class<?> type = field.getType();
					Object fileNameValue = parseParam(type, value + "");
					Method method = parseMethod(c, instance, fieldName, fileNameValue);
					method.invoke(instance, fileNameValue);
				}
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void convertTOJavaBean(JSONObject jsonObject, Class<?> c, Object instance) {
		List<Field> fields = getAllFields(c);
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				if (fieldName.equals("serialVersionUID")) {
					continue;
				}
				if (jsonObject.has(fieldName)) {
					Object value = jsonObject.get(fieldName);
					Class<?> type = field.getType();
					Object fileNameValue = parseParam(type, value + "");
					Method method = parseMethod(c, instance, fieldName, fileNameValue);
					method.invoke(instance, fileNameValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新对象的数据，配合Hibernate的update方法使用,对象必须是标准的javaBean
	 * 
	 * @param original
	 *            数据库里的对象
	 * @param newObject
	 *            新对象
	 */
	public static void updateObjectData(Object original, Object newObject) {
		try {
			Class<?> oClass = original.getClass();
			Method[] methods = oClass.getMethods();
			for (Method method : methods) {
				method.setAccessible(true);
				String getMethodName = method.getName();
				if (getMethodName.equals("getClass")) {
					continue;
				}
				if (getMethodName.startsWith("get")) {
					Object value = method.invoke(newObject);
					if(value != null){
						//Class<?>[] interfaces = value.getClass().getInterfaces();
						if(!isListOrSetOrMap(value)){
							String setMethodName = "set" + getMethodName.substring(3);
							Method setMethod = oClass.getMethod(setMethodName, value.getClass());
							setMethod.invoke(original, value);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 格式化
	 * 
	 * @param jsonStr
	 * @return
	 * 
	 * 
	 */
	public static String format(String jsonStr) {
		if (null == jsonStr || "".equals(jsonStr))
			return "";
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		boolean isInQuotationMarks = false;
		for (int i = 0; i < jsonStr.length(); i++) {
			last = current;
			current = jsonStr.charAt(i);
			switch (current) {
			case '"':
				if (last != '\\') {
					isInQuotationMarks = !isInQuotationMarks;
				}
				sb.append(current);
				break;
			case '{':
			case '[':
				sb.append(current);
				if (!isInQuotationMarks) {
					sb.append('\n');
					indent++;
					addIndentBlank(sb, indent);
				}
				break;
			case '}':
			case ']':
				if (!isInQuotationMarks) {
					sb.append('\n');
					indent--;
					addIndentBlank(sb, indent);
				}
				sb.append(current);
				break;
			case ',':
				sb.append(current);
				if (last != '\\' && !isInQuotationMarks) {
					sb.append('\n');
					addIndentBlank(sb, indent);
				}
				break;
			default:
				sb.append(current);
			}
		}
	
		return sb.toString();
	}
	/**
	 *  根据字段顺序排列JSON
	 * 
	 * @param jsonObject
	 * @param entityClass
	 * @return
	 */
	public static String resetJSON(JSONObject jsonObject, Class<?> entityClass) {
		StringBuilder stringBuilder = null;
		if (jsonObject != null) {
			List<Field> fields = getAllFields(entityClass);
			if (fields != null && fields.size() > 0) {
				stringBuilder = new StringBuilder();
				stringBuilder.append("{");
				try {
					for (int i = 0; i < fields.size(); i++) {
						Field field = fields.get(i);
						String fieldName = field.getName();
						if (!fieldName.equals("serialVersionUID")) {
							Object fieldValue = null;
							if (jsonObject.has(fieldName)) {
								fieldValue = jsonObject.get(fieldName);
							}
							stringBuilder.append("\"").append(fieldName).append("\":\"")
							.append(fieldValue == null ? "" : fieldValue)
							.append(i == fields.size() - 1 ? "\"}" : "\",");
	
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuilder == null ? null : stringBuilder.toString();
	
	}
	/**
	 * 处理集合对象
	 * @param value
	 * @return
	 */
	public static Object handListOrSetOrMap(Object value){
		JSONArray jsonArray=new JSONArray();
		if (value instanceof List) {
			List<?> list=(List<?>) value;
			for (Object lo : list) {
				if(lo instanceof String){
					jsonArray.put(lo);
				}
				else{
					JSONObject json = getJsonObject(lo);
					jsonArray.put(json);
				}
			}
		}else if(value instanceof Map){
			//map的解析工作还没有完善
			Map<?, ?> map=(Map<?, ?>) value;
			Iterator<?> iterator = map.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
				Object entryKey = entry.getKey();
				Object entryValue = entry.getValue();
				JSONObject json=new JSONObject();
				
				try {
					if(isBaseClass(entryValue)){
						json.put(entryKey+"", entryValue);
					}else{
						json.put(entryKey+"", getJsonObject(entryValue));											
					}					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				jsonArray.put(json);
			}
		}else if(value instanceof Set){
			Set<?> set=(Set<?>) value;
			Iterator<?> iterator = set.iterator();
			while(iterator.hasNext()){
				Object so = iterator.next();
				JSONObject json = getJsonObject(so);
				jsonArray.put(json);
			}
		}
		return jsonArray.length()>0?jsonArray:"";
	}
	/**
	 * 如果对象是集合类，则返回true
	 * @param o
	 */
	public static boolean isListOrSetOrMap(Object value) {
		return value instanceof List || value instanceof Set || value instanceof Map;
	}

	/**
	 * 判断是否是日期类型
	 * @param value
	 * @return
	 */
	private static boolean isDateClass(Object value) {
		return value.getClass() == Date.class || value.getClass() == Timestamp.class;
	}
	/**
	 * 基本类型
	 * @param value
	 * @return
	 */
	public static boolean isBaseClass(Object value) {
		return value.getClass() == Integer.class || value.getClass() == String.class
				|| value.getClass() == Float.class || value.getClass() == Long.class
				|| value.getClass() == Byte.class || value.getClass() == Timestamp.class
				|| value.getClass() == Date.class || value.getClass() == Boolean.class
				|| value.getClass() == LocalTime.class || value.getClass() == LocalDate.class
				|| value.getClass() == LocalDateTime.class || value.getClass() == Instant.class
				|| value.getClass() == JSONArray.class || value.getClass() == JSONObject.class;
	}

	/**
	 * 通过方法获得属性的名字
	 * 
	 * @param method
	 * @return
	 */
	private static String getFieldNameByMethod(Method method) {
		String name = method.getName();
		if (name.startsWith("get")) {
			return name.substring(3, 4).toLowerCase() + name.substring(4);
		} else if (name.startsWith("is")
				&& (method.getReturnType() == Boolean.class || method.getReturnType() == boolean.class)) {
			return name.substring(2, 3).toLowerCase() + name.substring(3);
		} else {
			return name;
		}
	}

	/**
	 * 是否包含集合框架
	 * @param interfaces
	 * @return
	 *//*
	private static boolean isContainsSetMapList(Class<?>[] interfaces) {
		if(interfaces != null && interfaces.length > 0){
			for (Class<?> c : interfaces) {
				if( c == Set.class || c == List.class || c ==Map.class ){
					return true;
				}
			}
		}
		return false;
	}*/
	/**
	 * 将参数转换成相应的类型,默认是String
	 * 
	 * @param ctype
	 * @param value
	 * @return
	 */
	private static Object parseParam(Class<?> ctype, String value) {
		Object result = value;
		try {
			if (ctype == Integer.class || ctype == int.class) {
				result = Integer.parseInt(value);
			} else if (ctype == Long.class || ctype == long.class) {
				result = Long.parseLong(value);
			} else if (ctype == Date.class) {
				result = DateUtil.parse(value, "yyyy-MM-dd HH:mm:ss");
			} else if (ctype == Boolean.class || ctype == boolean.class) {
				result = Boolean.parseBoolean(value);
			} else if (ctype == Short.class || ctype == short.class) {
				result = Short.parseShort(value);
			} else if (ctype == Double.class || ctype == double.class) {
				result = Double.parseDouble(value);
			} else if (ctype == Byte.class || ctype == byte.class) {
				result = Byte.parseByte(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 解析方法
	 * 
	 * @param c
	 * @param instance
	 * @param key
	 * @param value
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Method parseMethod(Class c, Object instance, String fieldName, Object value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		String methodName = null;
		Method method = null;
		methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		method = c.getMethod(methodName, value.getClass());
		if (method != null) {
			return method;
		} else {
			methodName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			method = c.getMethod(methodName, value.getClass());
		}
		return method;
	};

	/**
	 * 取得实体类的参考文档
	 * 
	 * @param entityClass
	 * @return
	 */
	public static JSONObject help(Class<?> entityClass) {
		String className = entityClass.getSimpleName();
		List<Field> fields = getAllFields(entityClass);
		JSONObject entityJSON = null;
		try {
			if (fields != null && fields.size() > 0) {
				entityJSON = new JSONObject();
				JSONObject jsonObject = new JSONObject();
				for (Field field : fields) {
					field.setAccessible(true);
					String name = field.getName();
					if (name.equals("serialVersionUID")) {
						continue;
					}
					String classType = field.getType().getSimpleName();
					ColumnDefinition columnDefinition = field.getAnnotation(ColumnDefinition.class);
					if (columnDefinition != null) {
						String value = columnDefinition.value();
						String[] enems = columnDefinition.enems();
						if (enems != null && enems.length > 0) {
							StringBuilder stringBuilder = new StringBuilder("");
							for (int i = 0; i < enems.length; i++) {
								stringBuilder.append(enems[i]);
								if (i != enems.length - 1) {
									stringBuilder.append(",");
								}
							}

							jsonObject.put(name, value + ",数据类型-" + classType + ",枚举值-" + stringBuilder.toString());
						} else {
							jsonObject.put(name, value + ",数据类型-" + classType);
						}
					} else {
						jsonObject.put(name, "数据类型-" + classType);
					}

				}
				entityJSON.put(className, jsonObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entityJSON;
	}

	/**
	 * 取得所有的字段
	 * 
	 * @param entityClass
	 * @return
	 */
	public static List<Field> getAllFields(Class<?> entityClass) {
		List<Field> fields = new ArrayList<Field>();
		while (entityClass != null) {// 采用递归的方式取得所子类及父类的字段
			fields.addAll(Arrays.asList(entityClass.getDeclaredFields()));
			entityClass = entityClass.getSuperclass();
		}
		return fields;
	}

	/**
	 * 添加space
	 * 
	 * @param sb
	 * @param indent
	 * 
	 * 
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append('\t');
		}
	}
}
