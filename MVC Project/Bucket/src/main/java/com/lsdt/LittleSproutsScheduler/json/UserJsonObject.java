package com.lsdt.LittleSproutsScheduler.json;

import java.util.List;

import com.lsdt.LittleSproutsScheduler.model.User;

public class UserJsonObject {
	int iTotalRecords;
	 
    int iTotalDisplayRecords;
 
    String sEcho;
 
    String sColumns;
 
    List<User> aaData;
 
    public int getiTotalRecords() {
	return iTotalRecords;
    }
 
    public void setiTotalRecords(int iTotalRecords) {
	this.iTotalRecords = iTotalRecords;
    }
 
    public int getiTotalDisplayRecords() {
	return iTotalDisplayRecords;
    }
 
    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
	this.iTotalDisplayRecords = iTotalDisplayRecords;
    }
 
    public String getsEcho() {
	return sEcho;
    }
 
    public void setsEcho(String sEcho) {
	this.sEcho = sEcho;
    }
 
    public String getsColumns() {
	return sColumns;
    }
 
    public void setsColumns(String sColumns) {
	this.sColumns = sColumns;
    }
 
    public List<User> getAaData() {
        return aaData;
    }
 
    public void setAaData(List<User> aaData) {
        this.aaData = aaData;
    }
}
