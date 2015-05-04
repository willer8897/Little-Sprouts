package org.lsdt.optaplannerLittleSprouts;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Child {
	private int id;
	private String name_first;
	private String name_mid_initial;
	private String name_last;
	private int parentid;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
	
	public Child(){}
	public Child(String name_first, String name_mid_initial, String name_last, int parentid, Date birthday){
		this.name_first = name_first;
		this.name_mid_initial = name_mid_initial;
		this.name_last = name_last;
		this.parentid = parentid;
		this.birthday = birthday;
	}
	
	public int getId() {
		return id;
	}
	public String getName_first() {
		return name_first;
	}
	public void setName_first(String name_first) {
		this.name_first = name_first;
	}
	public String getName_mid_initial() {
		return name_mid_initial;
	}
	public void setName_mid_initial(String name_mid_initial) {
		this.name_mid_initial = name_mid_initial;
	}
	public String getName_last() {
		return name_last;
	}
	public void setName_last(String name_last) {
		this.name_last = name_last;
	}
	public void setId(int id) {
		this.id = id;
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
