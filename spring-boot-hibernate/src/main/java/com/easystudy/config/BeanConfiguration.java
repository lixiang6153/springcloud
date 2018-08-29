package com.easystudy.config;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @PropertySource("classpath:datasource.properties")注解，就可以免去我们自己写读取配置文件的麻烦
 * @author lixiang6153@126.com
 *
 */
@Configuration													// 表示这个类是一个spring 配置类，一般这里面会定义Bean，会把这个类中bean加载到spring容器中
@EnableTransactionManagement 									// 启注解事务管理，同xml配置： <tx:annotation-driven />
@ComponentScan("com.easystudy")									// 如果不设置basePackage的话 默认会扫描包的所有类,同xml配置：<context:component-scan />
@Import(HikariSource.class)										// 引入从配置
@EnableJpaRepositories(value = "com.easystudy.dao.impl",
					   entityManagerFactoryRef = "writeEntityManagerFactory",
					   transactionManagerRef="writeTransactionManager")
public class BeanConfiguration {	
	@Autowired
	private DataSource dataSource;
	@Autowired
	JpaProperties jpaProperties;

	@Value("${spring.jpa.hibernate.dialect}")
    private String dialect;
    @Value("${spring.jpa.format-sql}")
    private String formatSql;
    @Value("${spring.jpa.show-sql}")
    private String showSql;	

    /**
     * EntityManagerFactory类似于Hibernate的SessionFactory,mybatis的SqlSessionFactory
     * 总之,在执行操作之前,我们总要获取一个EntityManager,这就类似于Hibernate的Session,mybatis的sqlSession.
     * 注入EntityManagerFactory有两种方式,一种是直接注入EntityManagerFactory,另一种是通过 
     * LocalContainerEntityManagerFactoryBean来间接注入.虽说这两种方法都是基于 
     * LocalContainerEntityManagerFactoryBean的,但是在配置上还是有一些区别
     * @return
     */
    @Bean(name = "writeEntityManagerFactory")
    @Primary
    public EntityManagerFactory writeEntityManagerFactory() {
      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
      factory.setJpaVendorAdapter(vendorAdapter);
      factory.setPackagesToScan("com.easystudy.model");
      factory.setDataSource(this.dataSource);						//数据源
      factory.setJpaPropertyMap(jpaProperties.getProperties());
      factory.afterPropertiesSet();									//在完成了其它所有相关的配置加载以及属性设置后,才初始化
      return factory.getObject();
    }
    
    /**
     * 配置事物管理器
     * @return
     */
    @Primary
    @Bean(name = "writeTransactionManager")
    public PlatformTransactionManager writeTransactionManager() {
      JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
      jpaTransactionManager.setEntityManagerFactory(this.writeEntityManagerFactory());
      return jpaTransactionManager;
    }
    
//	// txManager事务开启
//  @Bean(name="txManager")
//  public HibernateTransactionManager txManager() throws SQLException {
//      HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
//      LocalSessionFactoryBean sessionFactoryBean = this.sessionFactory();
//      hibernateTransactionManager.setSessionFactory(sessionFactoryBean.getObject());
//      return hibernateTransactionManager;
//  }
    
//  @Primary
//  @Bean("localContainerEntityManagerFactoryBean")
//  public LocalContainerEntityManagerFactoryBean companyEntityManagerFactory() {
//    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//    entityManagerFactoryBean.setPackagesToScan("com.easystudy");
//    entityManagerFactoryBean.setPersistenceUnitName("company");
//    entityManagerFactoryBean.setDataSource(this.dataSource);
//
//    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//    adapter.setDatabase(Database.MYSQL);
//    adapter.setShowSql(true);
//    adapter.setGenerateDdl(false);
//
//    entityManagerFactoryBean.setJpaVendorAdapter(adapter);
//
//    return entityManagerFactoryBean;
//  }
  
//	@Primary
//	@Bean
//	public PlatformTransactionManager companyTransactionManager() {
//	    JpaTransactionManager transactionManager = new JpaTransactionManager();
//	    transactionManager.setEntityManagerFactory((companyEntityManagerFactory().getObject()));
//
//	    return transactionManager;
//	}

    /**
     * 会话工厂配置
     * @return
     * @throws SQLException
     */
	@Bean
	public LocalSessionFactoryBean sessionFactory() throws SQLException {
		// 设置数据源
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(this.dataSource);

		// 设置hibernate
		Properties properties1 = new Properties();
		properties1.setProperty("hibernate.dialect", dialect);
		properties1.setProperty("hibernate.show_sql", showSql);
		properties1.setProperty("hibernate.format_sql", formatSql);
		localSessionFactoryBean.setHibernateProperties(properties1);

		// 设置扫描的实体包
		localSessionFactoryBean.setPackagesToScan("com.easystudy.model");

		// 指定映射文件的文件夹,实体与xml映射文件目录-此处没有
		//ResourceLoader resourceLoader = new DefaultResourceLoader();
		//Resource resource = resourceLoader.getResource("classpath:/src/main/resources");
		//localSessionFactoryBean.setMappingDirectoryLocations(resource);
		
		return localSessionFactoryBean;
	}
    
    // 文件上传
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }
    
    // 国际化配置
    @Bean
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("text/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Bean
    public CookieLocaleResolver localeResolver(){
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("Language");
        localeResolver.setCookieMaxAge(604800);
        localeResolver.setDefaultLocale(new Locale("zh_CN"));
        return localeResolver;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        return new LocaleChangeInterceptor();
    }
	
	// JdbcTemplate配置
    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        try {
            jdbcTemplate.setDataSource(this.dataSource);
            jdbcTemplate.setLazyInit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jdbcTemplate;
    }
    
    // Hibernate模版配置
    @Bean
    public HibernateTemplate hibernateTemplate(EntityManagerFactory entityManagerFactory) throws SQLException {
        HibernateTemplate hibernateTemplate = new HibernateTemplate();
        LocalSessionFactoryBean sessionFactory = this.sessionFactory();
        hibernateTemplate.setSessionFactory(sessionFactory.getObject());
        return hibernateTemplate;
    }
}
