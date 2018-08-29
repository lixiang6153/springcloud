package com.easystudy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="rights")
@Data
@DynamicInsert(true)
@DynamicUpdate(true)
public class Rights {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
    private Long id;
    private String name;
    private String value;
    
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch=FetchType.LAZY, mappedBy = "rights")
	private List<Role> roles = new ArrayList<Role>();
}
