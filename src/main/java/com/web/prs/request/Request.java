package com.web.prs.request;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.web.prs.user.User;

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=100, nullable=false)
	private String description;
	
	@Column(length=255, nullable=false)
	private String justification;
	
	@Column(length=10, nullable=false)
	private Date submittedDate;
	
	@Column(length=10, nullable=false)
	private Date dateNeeded;
	
	@Column(columnDefinition="DECIMAL 10,2 NOT NULL")
	private double total;
	
	@Column(length=22, nullable=false)
	private String status;
	
	@Column(length=22, nullable=false)
	private String deliveryMode;
	
	@Column(length=255, nullable=false)
	private String reasonForRejection;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="userId")
	private User user;
	
	
	public Request() {}
	public Request(int id, String description, String justification, Date submittedDate, Date dateNeeded, double total,
			String status, String deliveryMode, String reasonForRejection, User user) {
		super();
		this.id = id;
		this.description = description;
		this.justification = justification;
		this.submittedDate = submittedDate;
		this.dateNeeded = dateNeeded;
		this.total = total;
		this.status = status;
		this.deliveryMode = deliveryMode;
		this.reasonForRejection = reasonForRejection;
		this.user = user;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	public Date getDateNeeded() {
		return dateNeeded;
	}
	public void setDateNeeded(Date dateNeeded) {
		this.dateNeeded = dateNeeded;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public String getReasonForRejection() {
		return reasonForRejection;
	}
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@Override
	public String toString() {
		return "Request [id=" + id + ", description=" + description + ", justification=" + justification
				+ ", submittedDate=" + submittedDate + ", dateNeeded=" + dateNeeded + ", total=" + total + ", status="
				+ status + ", deliveryMode=" + deliveryMode + ", reasonForRejection=" + reasonForRejection + ", user="
				+ user + "]";
	}
	
}
