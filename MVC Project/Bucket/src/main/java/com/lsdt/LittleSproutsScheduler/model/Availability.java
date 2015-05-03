package com.lsdt.LittleSproutsScheduler.model;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

@Embeddable
@Table(name="Availability")
public class Availability {
	
	@Id
	@GeneratedValue
	private int id;
	
	@NotEmpty
	private Date weekstart;
	
	private String monhours;
	private String tuehours;
	private String wedhours;
	private String thuhours;
	private String frihours;
	
	public Availability(){}
	public Availability(Date weekstart, String monhours, String tuehours, String wedhours, String thuhours, String frihours){
		this.weekstart = weekstart;
		this.monhours = monhours;
		this.tuehours = tuehours;
		this.wedhours = wedhours;
		this.thuhours = thuhours;
		this.frihours = frihours;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getWeekstart() {
		return weekstart;
	}
	public void setWeekstart(Date weekstart) {
		this.weekstart = weekstart;
	}
	public String getMonhours() {
		return monhours;
	}
	public void setMonhours(String monhours) {
		this.monhours = monhours;
	}
	public String getTuehours() {
		return tuehours;
	}
	public void setTuehours(String tuehours) {
		this.tuehours = tuehours;
	}
	public String getWedhours() {
		return wedhours;
	}
	public void setWedhours(String wedhours) {
		this.wedhours = wedhours;
	}
	public String getThuhours() {
		return thuhours;
	}
	public void setThuhours(String thuhours) {
		this.thuhours = thuhours;
	}
	public String getFrihours() {
		return frihours;
	}
	public void setFrihours(String frihours) {
		this.frihours = frihours;
	}
	
	@Override
	public String toString() {
		return "Availability [id=" + id + ", weekstart=" + weekstart
				+ ", monhours=" + monhours + ", tuehours=" + tuehours
				+ ", wedhours=" + wedhours + ", thuhours=" + thuhours
				+ ", frihours=" + frihours + "]";
	}
}
