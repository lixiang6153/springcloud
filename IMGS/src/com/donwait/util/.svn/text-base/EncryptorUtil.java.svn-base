package com.donwait.util;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.TextEncryptor;

public class EncryptorUtil {
	private static PasswordEncryptor passwordEncryptor;
	private static TextEncryptor textEncryptor;
	static{
		passwordEncryptor = new BasicPasswordEncryptor();
		textEncryptor = new BasicTextEncryptor();
	}
	/**
	 * 对密码加密
	 * @param password
	 * @return
	 */
	public static String encryptPassword(String password){
		return passwordEncryptor.encryptPassword(password);
	}
	/**
	 * 比较密码是否与数据库的一致
	 * @param inputPasword 用户输入的密码
	 * @param oriPassword 数据库中的密码
	 * @return
	 */
	public static boolean checkPassword(String inputPasword,String oriPassword){
		return passwordEncryptor.checkPassword(inputPasword,oriPassword);
		
	};
	/**
	 * 对字符串加密
	 * @param text
	 * @return
	 */
	public static String encryptText(String text){
		return textEncryptor.encrypt(text);
	}
}
