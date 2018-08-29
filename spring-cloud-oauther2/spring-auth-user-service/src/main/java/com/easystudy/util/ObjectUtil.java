package com.easystudy.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

/**
 * 用于获得JSON对象的类,实体类建议使用标准的javaBean
 * 
 * @author Administrator
 *
 */
public class ObjectUtil {

	/**
	 * 将json对象转换成javaBean
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

	/**
	 * 将参数转换成相应的类型,默认是String
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
	 * 取得所有的字段
	 */
	public static List<Field> getAllFields(Class<?> entityClass) {
		List<Field> fields = new ArrayList<Field>();
		while (entityClass != null) {// 采用递归的方式取得所子类及父类的字段
			fields.addAll(Arrays.asList(entityClass.getDeclaredFields()));
			entityClass = entityClass.getSuperclass();
		}
		return fields;
	}
}
