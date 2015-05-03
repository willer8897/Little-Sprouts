package com.lsdt.LittleSproutsScheduler.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="Schedule")
public class Schedule {
	
	@Id
	@GeneratedValue
	private int account_id;
	
	@NotEmpty
	private Date date;
	
	@NotEmpty
	private int room_id;
	
	private String time_start;
	private String time_end;
	
	public Schedule(){}
	public Schedule(Date date, int room_id, String time_start, String time_end){
		this.date = date;
		this.room_id = room_id;
		this.time_start = time_start;
		this.time_end = time_end;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	
	@Override
	public String toString() {
		return "Schedule [account_id=" + account_id + ", date=" + date
				+ ", room_id=" + room_id + ", time_start=" + time_start
				+ ", time_end=" + time_end + "]";
	}
}
