package com.web.prs.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(name = "UIDX_userName", columnNames = {"id","userName"}))
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=20, name="userName", nullable=false)
	private String userName;
	
	@Column(length=10, name="Password", nullable=false)
	private String password;
	
	@Column(length=20, name="firstName", nullable=false)
	private String firstName;
	
	@Column(length=20, name="lastName", nullable=false)
	private String lastName;
	
	@Column(length=12, name="phone", nullable=false)
	private String phone;
	
	@Column(length=75, name="email", nullable=false)
	private String email;
	
	@Column(length=5, name="reviewer", nullable=false)
	private boolean reviewer;
	
	@Column(length=5, name="admin", nullable=false)
	private boolean admin;
	
	public User() {}
	public User(int id, String userName, String password, String firstName, String lastName, String phone, String email,
			boolean reviewer, boolean admin) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.reviewer = reviewer;
		this.admin = admin;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isReviewer() {
		return reviewer;
	}
	public void setReviewer(boolean reviewer) {
		this.reviewer = reviewer;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phone=" + phone + ", email=" + email + ", reviewer=" + reviewer
				+ ", admin=" + admin + "]";
	}
	
}
