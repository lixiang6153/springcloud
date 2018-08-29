package com.easystudy.model;

import java.io.Serializable;
/**
 * 联合主键
 * @author Administrator
 *
 */
public class UserPK implements Serializable {
	public UserPK(String siteId, String userName) {
		super();
		this.siteId = siteId;
		this.userName = userName;
	}
	private static final long serialVersionUID = 1L;
	private String siteId ;				/* 站点ID			*/
	private String userName ;			/* 注册的用户名,保证在站点的唯一性*/
	public UserPK() {
		// TODO Auto-generated constructor stub
	}
	

	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteid) {
		this.siteId = siteid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPK other = (UserPK) obj;
		if (siteId == null) {
			if (other.siteId != null)
				return false;
		} else if (!siteId.equals(other.siteId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
}
