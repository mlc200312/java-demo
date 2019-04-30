package com.yuan.forstudy.entity;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Model implements Serializable {

	private String id;
	private String name;
	private Date cretaeDate = new Date();

	public Model() {
		super();
	}

	public Model(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCretaeDate() {
		return cretaeDate;
	}

	public void setCretaeDate(Date cretaeDate) {
		this.cretaeDate = cretaeDate;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", name=" + name + ", cretaeDate=" + cretaeDate + "]";
	}

}
