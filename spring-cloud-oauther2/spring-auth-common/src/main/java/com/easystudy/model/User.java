package com.easystudy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data											// lombok自动生成getter、setter、构造、tostring等方法
@JsonInclude(JsonInclude.Include.NON_NULL)		// jackSon注解-注解不返回null值字段-如果值为null,则不返回
@Entity
@Table(name = "user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class User implements Serializable {
    private static final long serialVersionUID = 3881610071550902762L;
    @Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
    private Long id;
    private String username;
    private String password;
    private String name;
    private Date birthday;
    private Long sex;
    private String email;
    private String phone;
    
    @JsonIgnore
	@ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@Fetch(FetchMode.JOIN)							/* 防止发出大量SQL语句给数据库造成压力 */
	@JoinTable
	(
		// 中间表名称
		name="userrole",
		// 中间表列为user_id，参考本类对应表的id字段
		joinColumns = {
						@JoinColumn(name="user_id", referencedColumnName="id"),
					  },
		// 中间表列为role_id，参考本类对应表的personId字段
		inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
	)
	private List<Role> roles = new ArrayList<Role>();
}
