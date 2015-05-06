package org.lsdt.optaplannerLittleSprouts;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Request {
	private int id;
	private Date date;
	
	 @Temporal(TemporalType.TIMESTAMP)
     private Date requestedstart;
	 
	 @Temporal(TemporalType.TIMESTAMP)
     private Date requestedend;
	 
	 private int availabilityid;
	 private String requestnote;
	 
	 public Request(){}
	 public Request(Date date, Date requestedstart, Date requestedend, int availability, String requestnote){
		 this.date = date;
		 this.requestedstart = requestedstart;
		 this.requestedend = requestedend;
		 this.availabilityid = availability;
		 this.requestnote = requestnote;
	 }
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getRequestedstart() {
		return requestedstart;
	}
	public void setRequestedstart(Date requestedstart) {
		this.requestedstart = requestedstart;
	}
	public Date getRequestedend() {
		return requestedend;
	}
	public void setRequestedend(Date requestedend) {
		this.requestedend = requestedend;
	}
	public int getAvailabilityid() {
		return availabilityid;
	}
	public void setAvailabilityid(int availabilityid) {
		this.availabilityid = availabilityid;
	}
	public String getRequestnote() {
		return requestnote;
	}
	public void setRequestnote(String requestnote) {
		this.requestnote = requestnote;
	}
}
