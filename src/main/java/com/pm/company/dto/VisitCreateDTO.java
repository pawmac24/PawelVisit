package com.pm.company.dto;

import com.pm.company.model.Status;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class VisitCreateDTO {

	@NotNull
	private Date start;

	@NotNull
	private Date end;

	@NotNull
	private boolean active;

	@NotNull
	private Long customerId;

	@NotNull
	private Long employeeId;

	@NotNull
	private int status;

	public VisitCreateDTO() {
	}

	public VisitCreateDTO(Date start, Date end, boolean active, Long customerId, Long employeeId, Status status) {
		this.start = start;
		this.end = end;
		this.active = active;
		this.customerId = customerId;
		this.employeeId = employeeId;
		setStatus(status);
	}

	public VisitCreateDTO(Date start, Date end, boolean active, Long customerId, Long employeeId, int status) {
		super();
		this.start = start;
		this.end = end;
		this.active = active;
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.status = status;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Status getStatus() {
		return Status.parse(this.status);
	}

	public void setStatus(Status status) {
		this.status = status.getValue();
	}
}
