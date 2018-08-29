package cn.itsoruce.springboot.utils;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;
/**
 * 我们代码就相当于做以下配置
 * <bean class="com.github.pagehelper.PageHelper">
 *    <property name="offsetAsPageNum" value="true"></property>
 *    <property name="rowBoundsWithCount" value="true"></property>
 *    <property name="reasonable" value="true"></property>
 * </bean>
 * 
 * 为什么不用上面的配置，springboot是为了简化配置，用代码来代替配置。
 * @author Administrator
 *
 */
@Configuration //是一段配置 
public class MyBatisConfiguration {
	
	@Bean //bean的配置
    public PageHelper pageHelper() {
		System.out.println("MyBatisConfiguration.pageHelper()");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}