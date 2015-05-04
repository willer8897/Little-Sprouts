package org.lsdt.optaplannerLittleSprouts;

public class Room {
	private int id;
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
}
