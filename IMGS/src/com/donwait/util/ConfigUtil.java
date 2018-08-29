package com.donwait.util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
	/**
	 * 取得根目录的配置文件
	 * @param fileName
	 * @return Properties 返回配置文件操作对象
	 * @throws Exception
	 */
	public static Properties getProperties(String fileName){
		Properties properties = null;
		try {
			properties = new Properties();
			InputStream in = ConfigUtil.class.getResourceAsStream("/"+fileName);
			properties.load(in);
			return properties;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
		
	}
}
