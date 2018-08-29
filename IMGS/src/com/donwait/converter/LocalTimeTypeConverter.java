package com.donwait.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
/**
 * 对LocalTime的类型转换
 * @author Administrator
 *
 */
public class LocalTimeTypeConverter extends DefaultTypeConverter{
	@SuppressWarnings({ "static-access", "rawtypes" })
	@Override
	public Object convertValue(Map<String, Object> context, Object value, Class toType) {
		if(toType == LocalTime.class){
			String[] params = (String[]) value;
			for (String string : params) {
				if(!string.trim().equals("")){
					LocalTime localTime = LocalTime.now().parse(string);
					return localTime;
				}
				return null;
			}
			
		}else if(toType == String.class){
			LocalTime localTime = (LocalTime) value;
			return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
		}
		
		return super.convertValue(context, value, toType);
	}
}
