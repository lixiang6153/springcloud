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
@Table(name="role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Role implements Serializable {
    private static final long serialVersionUID = 2179037393108205286L;
    @Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
    private Long id;
    private String name;
    private String value;
    private String tips;
    //@PrePersist
    private Date createTime = new Date();
    //@PreUpdate
    private Date updateTime = new Date();
    private Long status;
    
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch=FetchType.LAZY, mappedBy = "roles")
	private List<User> users = new ArrayList<User>();
    
    @ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    /* 防止发出大量SQL语句给数据库造成压力 */
	@Fetch(FetchMode.JOIN)							
	@JoinTable
	(
		// 中间表名称
		name="rolerights",
		// 中间表列为user_id，参考本类对应表的id字段
		joinColumns = {
						@JoinColumn(name="role_id", referencedColumnName="id"),
					  },
		// 中间表列为role_id，参考本类对应表的personId字段
		inverseJoinColumns = {@JoinColumn(name="right_id", referencedColumnName="id")}
	)
    @JsonIgnore
	private List<Rights> rights = new ArrayList<Rights>();	
}
