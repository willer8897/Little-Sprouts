package com.lsdt.LittleSproutsScheduler.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

@Embeddable
@Table(name="Availability")
public class Availability implements Serializable {

	private static final long serialVersionUID = -4669795199326917656L;

	@OneToOne(fetch=FetchType.EAGER, targetEntity=Request.class)
	@JoinColumn(name="account_id")
	@NotEmpty
	private int account_id;
	
	@NotEmpty
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
	
	@OneToOne(fetch=FetchType.EAGER, targetEntity=Request.class)
	@JoinColumn(name="is_Child")
	@Column(name="isChild", columnDefinition = "bit")
	//@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isChild;
	
	@Id
	@GeneratedValue
	private int availability_id;
	
	public Availability(){}
	public Availability(Date weekstart, String monhours, String tuehours, String wedhours, String thuhours, String frihours, boolean isChild){
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
	public int getAvailability_id() {
		return availability_id;
	}
	public void setAvailability_id(int availability_id) {
		this.availability_id = availability_id;
	}
	public boolean getIsChild() {
		return isChild;
	}
	public void setIsChild(boolean isChild) {
		this.isChild = isChild;
	}
	@Override
	public String toString() {
		return "Availability [id=" + account_id + ", weekstart=" + week_start
				+ ", monhours=" + monhours + ", tuehours=" + tuehours
				+ ", wedhours=" + wedhours + ", thuhours=" + thuhours
				+ ", frihours=" + frihours + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + account_id;
		result = prime * result
				+ ((frihours == null) ? 0 : frihours.hashCode());
		result = prime * result
				+ ((monhours == null) ? 0 : monhours.hashCode());
		result = prime * result
				+ ((thuhours == null) ? 0 : thuhours.hashCode());
		result = prime * result
				+ ((tuehours == null) ? 0 : tuehours.hashCode());
		result = prime * result
				+ ((wedhours == null) ? 0 : wedhours.hashCode());
		result = prime * result
				+ ((week_start == null) ? 0 : week_start.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Availability other = (Availability) obj;
		if (account_id != other.account_id)
			return false;
		if (frihours == null) {
			if (other.frihours != null)
				return false;
		} else if (!frihours.equals(other.frihours))
			return false;
		if (isChild != other.isChild)
			return false;
		if (monhours == null) {
			if (other.monhours != null)
				return false;
		} else if (!monhours.equals(other.monhours))
			return false;
		if (thuhours == null) {
			if (other.thuhours != null)
				return false;
		} else if (!thuhours.equals(other.thuhours))
			return false;
		if (tuehours == null) {
			if (other.tuehours != null)
				return false;
		} else if (!tuehours.equals(other.tuehours))
			return false;
		if (wedhours == null) {
			if (other.wedhours != null)
				return false;
		} else if (!wedhours.equals(other.wedhours))
			return false;
		if (week_start == null) {
			if (other.week_start != null)
				return false;
		} else if (!week_start.equals(other.week_start))
			return false;
		return true;
	}
}
