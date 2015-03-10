package org.lsdt.optaplannerLittleSprouts;

public class Child {
	private int id;
	private String name;
	private int parentid;
	private int age;
	
	public Child(){}
	public Child(String name, int parentid, int age){
		this.name = name;
		this.parentid = parentid;
		this.age = age;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
