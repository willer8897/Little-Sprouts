package com.lsdt.LittleSproutsScheduler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="Room")
public class Room {
	
	@Id
	@GeneratedValue
	private int id;
	
	@NotEmpty
	private int cap;
	
	public Room(){}
	public Room(int cap){
		this.cap = cap;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCap() {
		return cap;
	}
	public void setCap(int cap) {
		this.cap = cap;
	}
	
	@Override
	public String toString() {
		return "Room [id=" + id + ", cap=" + cap + "]";
	}
}
