package com.easystudy.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariDataSource;

@Configuration	
@PropertySource("classpath:datasource.properties")				// 配置文件路径-配置dataSource的Bean
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class HikariSource {
	private String url;
	private String username;
	private String password;
	private String driverClassName;
	private boolean defaultAutoCommit;
	private boolean autoCommit;
	private int maximumPoolSize;
	private int connectionTimeout;
	private int maxActive;
	private int maxIdle;
	private int maxIdleMillis;
	private int maxWait;
	private int minIdle;
	private int initialSize;
	private String validationQuery;
	private int timeBetweenEvictionRunsMillis;
	private int maxDetectTimeMillis;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public boolean isDefaultAutoCommit() {
		return defaultAutoCommit;
	}
	public void setDefaultAutoCommit(boolean defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}
	public boolean isAutoCommit() {
		return autoCommit;
	}
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}
	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}
	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getMaxIdleMillis() {
		return maxIdleMillis;
	}
	public void setMaxIdleMillis(int maxIdleMillis) {
		this.maxIdleMillis = maxIdleMillis;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}	
	public int getMaxDetectTimeMillis() {
		return maxDetectTimeMillis;
	}
	public void setMaxDetectTimeMillis(int maxDetectTimeMillis) {
		this.maxDetectTimeMillis = maxDetectTimeMillis;
	}
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	@Bean(destroyMethod = "close")     	// 声明其为Bean实例
    @Primary  							// 在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() throws SQLException {
		
        HikariDataSource datasource = new HikariDataSource();

        datasource.setJdbcUrl(this.url);
        datasource.setUsername(this.username);
        datasource.setPassword(this.password);
        datasource.setDriverClassName(this.driverClassName);

        //configuration
        datasource.setAutoCommit(this.autoCommit);
        datasource.setMaximumPoolSize(this.maximumPoolSize);
        datasource.setIdleTimeout(this.maxIdleMillis);
        datasource.setConnectionTimeout(this.connectionTimeout);
        datasource.setMaxLifetime(this.maxDetectTimeMillis);
        datasource.setMinimumIdle(this.minIdle);
        datasource.setValidationTimeout(this.timeBetweenEvictionRunsMillis);

        return datasource;
    }
}
