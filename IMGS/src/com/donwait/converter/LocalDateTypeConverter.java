package com.donwait.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;


import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
/**
 * 对LocalTime的类型转换
 * @author Administrator
 *
 */
public class LocalDateTypeConverter extends DefaultTypeConverter{
	@SuppressWarnings({ "static-access", "rawtypes" })
	@Override
	public Object convertValue(Map<String, Object> context, Object value, Class toType) {
		if(toType == LocalDate.class){
			String[] params = (String[]) value;
			for (String string : params) {
				if(!string.trim().equals("")){
					LocalDate localTime = LocalDate.now().parse(string);
					return localTime;
				}
				return null;
			}
			
		}else if(toType == String.class){
			LocalDate localDate = (LocalDate) value;
			return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
		}
		
		return super.convertValue(context, value, toType);
	}
}
