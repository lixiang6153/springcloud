package com.easystudy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="userrole")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UserRole {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private Long id;			// 主键id
	@Column(name="user_id")
	private Long user_id;	// 用户id
	@Column(name="role_id")
	private Long role_id;
}
