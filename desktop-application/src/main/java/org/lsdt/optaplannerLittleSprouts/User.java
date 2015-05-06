package org.lsdt.optaplannerLittleSprouts;

public class User {
	private int id;
	private char type;
	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	private String phonenumber;
	
	public User(){}
	public User(char type, String userName, String passWord, String email, String firstName, String lastName, String phoneNumber){
		this.type = type;
		this.username = userName;
		this.password = passWord;
		this.email = email;
		this.firstname = firstName;
		this.lastname = lastName;
		this.phonenumber = phoneNumber;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastName) {
		this.lastname = lastName;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phoneNumber) {
		this.phonenumber = phoneNumber;
	}
	
	
}
