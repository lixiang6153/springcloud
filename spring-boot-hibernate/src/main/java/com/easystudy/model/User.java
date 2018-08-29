package com.easystudy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * 
*用户信息实体
*如果是站点用户
*创建用户时基本信息必须已经添加到系统中[输入完身份证自动显示详细信息到界面] 
*如果是非站点用户
*	可以使用针对系统用户创建接口进行添加[最好普通用户通过注册接口进行注册，以免增加管理员负担]
*
 * @author Administrator
 *
 */
@Entity
@IdClass(UserPK.class)
@Table(name="user")
public class User extends Extender {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="siteId")
	private String siteId ;							/* 站点ID			*/
	@Id
	@Column(name="username")
	private String userName ;						/* 注册的用户名,保证在站点的唯一性*/
	@Column(name="password")
	private String password ;						/* 密 码			*/
	@Column(name="enable")
	private Boolean enable ;						/* 用户禁用状态		*/
	@Column(name="registertime")
	private Date registerTime ;						/* 注册时间		*/
	@Column(name="personidtype")
	private Integer personIdType ;					/* 人员证件类型		*/
	@Column(name="personid")
	private String personId ;						/* 证件号码		*/
	@Column(name="publickey")
	private String publicKey ;						/* 公钥			*/
	@Column(name="privatekey")
	private String privateKey ;						/* 私钥			*/
	@Column(name="aeskey")
	private String aesKey ;							/* 128、192、256	*/
	private String token ;							/* 用户token		*/
	@Column(name="lasttime")
	private Date lastTime ;							/* 最后访问时间		*/
	@Column(name="authority")
	private Integer authority;						/* 权限			*/

	@ManyToMany(cascade={CascadeType.PERSIST}, fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)//防止在立即加载中查询数量不正确
	@JoinTable
	(
			name="userroles",
			// 本表的主键
			joinColumns = {	
							@JoinColumn(name="siteid",referencedColumnName="siteid"),  
							@JoinColumn(name="username",referencedColumnName="username")
						  },
			// 反向表参考列
			inverseJoinColumns = {@JoinColumn(name="roletype")}
	)
	//@OrderBy("username")
	private List<Role> roles = new ArrayList<Role>();  /* 角色			*/

	
	public User(){
		enable = true;
		personIdType = /*IdentifyType.ID_TYPE_CERTIFICATE.ordinal()*/0;
	}
	
	public void setAuthority(Integer authority) {
		this.authority = authority;
	}
	public Integer getAuthority() {
		return authority;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
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
	public Integer getPersonIdType() {
		return personIdType;
	}
	public void setPersonIdType(Integer personIdType) {
		this.personIdType = personIdType;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getAesKey() {
		return aesKey;
	}
	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void addRole(Role role){
		this.roles.add(role);
	}
	public User (String userName,String password){
		this.userName = userName;
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [siteId=" + siteId + ", userName=" + userName + ", password=" + password + ", enable=" + enable
				+ ", registerTime=" + registerTime + ", personIdType=" + personIdType + ", personId=" + personId
				+ ", publicKey=" + publicKey + ", privateKey=" + privateKey + ", aesKey=" + aesKey + ", token=" + token
				+ ", lastTime=" + lastTime + ", authority=" + authority + "]";
	}	
}
