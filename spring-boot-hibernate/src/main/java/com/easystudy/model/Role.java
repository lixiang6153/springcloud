package com.easystudy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.easystudy.annotation.JSONEnable;
/**
 * 角色实体类
 * @author Administrator
 *
 */
@Entity
@Table(name = "roles", catalog = "imps", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Role extends Extender{
	private static final long serialVersionUID = 1L;
	@Column(name="type")
	private Integer type;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	private List<Right> rights = new ArrayList<Right>();
	private Set<User> users = new HashSet<User>();

	public Role(){

	}

	public Role(Integer type,String name,String description){
		this.type = type;
		this.name = name;
		this.description = description;
	}

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "type", unique = true, nullable = false)
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@JoinTable
	(
			name="rolesrights",
			joinColumns = {@JoinColumn(name="roletype", referencedColumnName="type")},
			inverseJoinColumns = {@JoinColumn(name="rightid",referencedColumnName="id")}
			)
	@JSONEnable("false")
	@Fetch(FetchMode.SELECT)
	public List<Right> getRights() {
		return rights;
	}
	public void setRights(List<Right> rights) {
		this.rights = rights;
	}
	@JSONEnable("false")
	@ManyToMany(cascade={CascadeType.REFRESH}, fetch=FetchType.LAZY, mappedBy = "roles")
	@Fetch(FetchMode.SELECT)
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Role [type=" + type + ", name=" + name + ", description=" + description + ", rights=" + rights
				+ ", users=" + users + "]";
	}
	
	


}
