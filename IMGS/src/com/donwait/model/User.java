package com.donwait.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class User extends Reserver implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String userName;
	private String password;
	private Integer type = 0;
	private Boolean enable = true;
	private Date registerTime = new Date();
	
	@Transient
	private Date lastAccessTime = new Date();
	@Transient
	private String token;
	@Transient
	private String newPassword;
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void update(){
		lastAccessTime = new Date();
	}
	
	@SuppressWarnings("deprecation")
	public Boolean expired(Integer seconds){
		return new Date().getSeconds() - lastAccessTime.getSeconds() > seconds? true : false;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
}