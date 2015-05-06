package org.lsdt.optaplannerLittleSprouts;

import java.util.Date;

public class Schedule {
	private int id;
	private Date date;
	private int room;
	private String time_start;
	private String time_end;
	
	public Schedule(){}
	public Schedule(Date date, int room, String time_start, String time_end){
		this.date = date;
		this.room = room;
		this.time_start = time_start;
		this.time_end = time_end;
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
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
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
}
