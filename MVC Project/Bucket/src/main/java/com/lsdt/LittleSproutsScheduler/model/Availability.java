package com.lsdt.LittleSproutsScheduler.model;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

@Embeddable
@Table(name="Availability")
public class Availability {
	
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="id", updatable=false, insertable=false)
	private int account_id;
	
	@Id
	@NotEmpty
	private Date week_start;
	
	private String monhours;
	private String tuehours;
	private String wedhours;
	private String thuhours;
	private String frihours;
	
	private int isChild;
	
	public Availability(){}
	public Availability(Date weekstart, String monhours, String tuehours, String wedhours, String thuhours, String frihours, int isChild){
		this.week_start = weekstart;
		this.monhours = monhours;
		this.tuehours = tuehours;
		this.wedhours = wedhours;
		this.thuhours = thuhours;
		this.frihours = frihours;
		this.isChild = isChild;
	}
	
	public int getId() {
		return account_id;
	}
	public void setId(int id) {
		this.account_id = id;
	}
	public Date getWeekstart() {
		return week_start;
	}
	public void setWeekstart(Date weekstart) {
		this.week_start = weekstart;
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
	
	public int getIsChild() {
		return isChild;
	}
	public void setIsChild(int isChild) {
		this.isChild = isChild;
	}
	@Override
	public String toString() {
		return "Availability [id=" + account_id + ", weekstart=" + week_start
				+ ", monhours=" + monhours + ", tuehours=" + tuehours
				+ ", wedhours=" + wedhours + ", thuhours=" + thuhours
				+ ", frihours=" + frihours + "]";
	}
}
