package org.lsdt.optaplannerLittleSprouts;

import java.util.Date;

public class Availability {
	private int id;
	private Date weekstart;
	private String monhours;
	private String tuehours;
	private String wedhours;
	private String thuhours;
	private String frihours;
	private int is_child;
	private int availability_id;
	
	public Availability(){}
	public Availability(Date weekstart, String monhours, String tuehours, String wedhours, String thuhours, String frihours, int is_child, int availability_id){
		this.weekstart = weekstart;
		this.monhours = monhours;
		this.tuehours = tuehours;
		this.wedhours = wedhours;
		this.thuhours = thuhours;
		this.frihours = frihours;
		this.is_child = is_child;
		this.availability_id = availability_id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getAvailability_id() {
		return availability_id;
	}
	public void setAvailability_id(int availability_id) {
		this.availability_id = availability_id;
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
	public int getIs_child() {
		return is_child;
	}
	public void setIs_child(int is_child) {
		this.is_child = is_child;
	}
}
