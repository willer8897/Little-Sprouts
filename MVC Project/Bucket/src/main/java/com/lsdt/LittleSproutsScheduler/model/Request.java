package com.lsdt.LittleSproutsScheduler.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Target;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Request")
public class Request {
	
	@Id
	@GeneratedValue
	private int request_id;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="MM.dd.yyyy")
	private Date request_requestDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date request_requestedStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date request_requestedEnd;
	
	@Target(Availability.class)
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="account_id", updatable=false, insertable=false)
	private int Availability_availability_id;
	
	private String request_note;

	public Request() {}
	public Request(Date request_requestDate, Date request_requestedStart, Date request_requestedEnd, int Availability_availability_id, String request_note) {
		this.request_requestDate = request_requestDate;
		this.request_requestedStart = request_requestedStart;
		this.request_requestedEnd = request_requestedEnd;
		this.Availability_availability_id = Availability_availability_id;
		this.request_note = request_note;
	}
	public int getRequest_id() {
		return request_id;
	}
	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}
	public Date getRequest_requestDate() {
		return request_requestDate;
	}
	public void setRequest_requestDate(Date request_requestDate) {
		this.request_requestDate = request_requestDate;
	}
	public Date getRequest_requestedStart() {
		return request_requestedStart;
	}
	public void setRequest_requestedStart(Date request_requestedStart) {
		this.request_requestedStart = request_requestedStart;
	}
	public Date getRequest_requestedEnd() {
		return request_requestedEnd;
	}
	public void setRequest_requestedEnd(Date request_requestedEnd) {
		this.request_requestedEnd = request_requestedEnd;
	}
	public int getAvailability_availability_id() {
		return Availability_availability_id;
	}
	public void setAvailability_availability_id(int availability_availability_id) {
		Availability_availability_id = availability_availability_id;
	}
	public String getRequest_note() {
		return request_note;
	}
	public void setRequest_note(String request_note) {
		this.request_note = request_note;
	}
	
	@Override
	public String toString() {
		return "Request [request_id=" + request_id + ", request_requestDate="
				+ request_requestDate + ", request_requestedStart="
				+ request_requestedStart + ", request_requestedEnd="
				+ request_requestedEnd + ", Availability_availability_id="
				+ Availability_availability_id + ", request_note="
				+ request_note + "]";
	}


}
