package com.easystudy.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class I18nTest {
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	// 浏览器的Locale 可以被传递，同HttpRequest等对象
	@RequestMapping("/i18n") 
	public String testI18n(Locale locale){
		String userName = messageSource.getMessage("i18n.username", null, locale);
		System.out.println("i18n：" + userName);
		return "i18n";
	}
	
	// 通过超链接切换本地语言-获取name=locale请求参数-->把上一步locale请求参数转为Locael对象
	// 获取LocaleResolver对象-》把Local对象设置为Session的属性(SessionLocaleResolver)-->从Session中获取Locale对象
	// 
	
	@RequestMapping("/switchLocale") 
	public String switchLocale(){
		return "i18n";
	}
}
