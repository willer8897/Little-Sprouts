package com.lsdt.LittleSproutsScheduler.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="Child")
public class Child {
	
	@Id
	@GeneratedValue
	private int id;
	
	@NotEmpty
	private String name_first;
	
	private char name_mid_initial;
	
	@NotEmpty
	private String name_last;
	
	@NotEmpty
	private int parent_id;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
	
	public Child(){}
	public Child(String name_first, char name_mid_initial, String name_last, int parent_id, Date birthday){
		this.name_first = name_first;
		this.name_mid_initial = name_mid_initial;
		this.name_last = name_last;
		this.parent_id = parent_id;
		this.birthday = birthday;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName_first() {
		return name_first;
	}
	public void setName_first(String name_first) {
		this.name_first = name_first;
	}
	public char getMid_initial() {
		return name_mid_initial;
	}
	public void setMid_initial(char mid_initial) {
		this.name_mid_initial = mid_initial;
	}
	public String getName_last() {
		return name_last;
	}
	public void setName_last(String name_last) {
		this.name_last = name_last;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
