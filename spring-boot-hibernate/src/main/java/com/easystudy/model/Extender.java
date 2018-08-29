package com.easystudy.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.dom4j.Element;
/**
 * 所有实体类的公共父类
 * @author Administrator
 *
 */
@MappedSuperclass
public class Extender implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected Integer reserver1;
	protected Integer reserver2;
	protected String reserver3;
	
	public Integer getReserver1() {
		return reserver1;
	}

	public void setReserver1(Integer reserver1) {
		this.reserver1 = reserver1;
	}

	public Integer getReserver2() {
		return reserver2;
	}

	public void setReserver2(Integer reserver2) {
		this.reserver2 = reserver2;
	}

	public String getReserver3() {
		return reserver3;
	}

	public void setReserver3(String reserver3) {
		this.reserver3 = reserver3;
	}

	public boolean fromXML(Element element){
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reserver1 == null) ? 0 : reserver1.hashCode());
		result = prime * result + ((reserver2 == null) ? 0 : reserver2.hashCode());
		result = prime * result + ((reserver3 == null) ? 0 : reserver3.hashCode());
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
		Extender other = (Extender) obj;
		if (reserver1 == null) {
			if (other.reserver1 != null)
				return false;
		} else if (!reserver1.equals(other.reserver1))
			return false;
		if (reserver2 == null) {
			if (other.reserver2 != null)
				return false;
		} else if (!reserver2.equals(other.reserver2))
			return false;
		if (reserver3 == null) {
			if (other.reserver3 != null)
				return false;
		} else if (!reserver3.equals(other.reserver3))
			return false;
		return true;
	}

	public String toXML(Element element){
		return "";
	}
}
