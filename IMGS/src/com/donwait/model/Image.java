package com.donwait.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "image")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Image extends Reserver implements java.io.Serializable {
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	private static final long serialVersionUID = 1L;
	@Id
	/*@GeneratedValue(generator = "image")    
	@GenericGenerator(name = "image", strategy = "native")  */
	private String id;							// 记录编号	
	private Integer type;						// 类型：0临时，1永久存储
	private String  url;						// 文件URL
	private Date uploadTime;					// 文件上传时间
	private Integer size;						// 文件大小
	
	public Image(){
		uploadTime = new Date();
	}
	
	public Image(int type, String url, int size){
		this.type = type;
		this.url = url;
		this.size = size;
		uploadTime = new Date();
	}
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
}