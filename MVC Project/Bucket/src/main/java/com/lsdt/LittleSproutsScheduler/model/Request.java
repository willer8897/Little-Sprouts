package com.lsdt.LittleSproutsScheduler.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="Request")
public class Request {

	@JoinColumn(name="account_id")
	private int account_id;
	
	private Date week_start;
	
	@Size(max=50)
	@NotEmpty
	@Column(name="0hours")
	private String monhours;
	
	@Size(max=50)
	@NotEmpty
	@Column(name="1hours")
	private String tuehours;
	
	@Size(max=50)
	@NotEmpty
	@Column(name="2hours")
	private String wedhours;
	
	@Size(max=50)
	@NotEmpty
	@Column(name="3hours")
	private String thuhours;
	
	@Size(max=50)
	@NotEmpty
	@Column(name="4hours")
	private String frihours;
	
	@Column(name="is_Child", columnDefinition = "bit")
	//@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isChild;
	
	@Size(max=255)
	@Column(name="request_note")
	private String request_note;
	
	@Id
	@GeneratedValue
	private int request_id;

	public Request() {}
	public Request(String monhours, String tuehours, String wedhours, String thuhours, String frihours, boolean isChild, String request_note) {
		this.monhours = monhours;
		this.tuehours = tuehours;
		this.wedhours = wedhours;
		this.thuhours =thuhours;
		this.frihours = frihours;
		this.request_note = request_note;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public Date getWeek_start() {
		return week_start;
	}
	public void setWeek_start(Date week_start) {
		this.week_start = week_start;
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
	public boolean getIsChild() {
		return isChild;
	}
	public void setIsChild(boolean isChild) {
		this.isChild = isChild;
	}
	public String getRequest_note() {
		return request_note;
	}
	public void setRequest_note(String request_note) {
		this.request_note = request_note;
	}
	public int getRequest_id() {
		return request_id;
	}
	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}
	@Override
	public String toString() {
		return "Request [account_id=" + account_id + ", week_start="
				+ week_start + ", monhours=" + monhours + ", tuehours="
				+ tuehours + ", wedhours=" + wedhours + ", thuhours="
				+ thuhours + ", frihours=" + frihours + ", isChild=" + isChild
				+ ", request_note=" + request_note + "]";
	}



}
