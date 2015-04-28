package org.lsdt.optaplannerLittleSprouts;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Child {
	private int id;
	private int present;
	private String name;
	private int parentid;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
	
	public Child(){}
	public Child(int present, String name, int parentid, int age, Date birthday){
		this.present = present;
		this.name = name;
		this.parentid = parentid;
		this.birthday = birthday;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPresent() {
		return present;
	}
	public void setPresent(int present) {
		this.present = present;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
