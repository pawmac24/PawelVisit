package com.pm.company.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "visit")
public class Visit {
	
	@Id
	@GeneratedValue
	@Column(name = "visit_id")
	private Long visitId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start", nullable = false)
	@NotNull
	private Date start;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end", nullable = false)
	@NotNull
	private Date end;
	
	@Column(name="active", nullable=false)
	@NotNull
	private boolean active;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	@NotNull
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	@NotNull
	private Employee employee;

	@Column(nullable=false)
	@NotNull
	private int status;

	public Visit() {
		super();
	}

	public Visit(Date start, Date end, boolean active, Customer customer, Employee employee, int status) {
		super();
		this.start = start;
		this.end = end;
		this.active = active;
		this.customer = customer;
		this.employee = employee;
		this.status = status;
	}

	public Visit(Long visitId, Date start, Date end, boolean active, Customer customer, Employee employee, int status) {
		this(start, end, active, customer, employee, status);
		this.visitId = visitId;

	}

	public Customer getCustomer() {
		return customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Date getEnd() {
		return end;
	}

	public Date getStart() {
		return start;
	}

	public Long getVisitId() {
		return visitId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	public Status getStatus() {
		return Status.parse(this.status);
	}

	public void setStatus(Status status) {
		this.status = status.getValue();
	}
}
