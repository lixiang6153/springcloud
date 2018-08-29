package com.easystudy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.easystudy.annotation.JSONEnable;

/**
 * 系统权限实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="rights")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Right extends Extender{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	private String id;
	@Column(name="value")
	private Integer value ;							/*	权限值		*/
	@Column(name="description")
	private String description ;					/* 	权限说明		*/
	@Column(name = "uri")							
	private String uri;								/* 	请求uri		*/
	@ManyToMany(cascade = {CascadeType.REFRESH}, fetch=FetchType.EAGER, mappedBy = "rights")
	private Set<Role> roles = new HashSet<Role>();				

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@JSONEnable
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
