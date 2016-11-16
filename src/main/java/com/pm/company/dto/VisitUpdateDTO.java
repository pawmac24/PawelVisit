package com.pm.company.dto;

import com.pm.company.model.Status;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class VisitUpdateDTO {
	
	@NotNull
	private Date start;

	@NotNull
	private Date end;

	@NotNull
	private boolean active;

	@NotNull
	private int status;

	public VisitUpdateDTO() {
	}

	public VisitUpdateDTO(Date start, Date end, boolean active, Status status) {
		this.start = start;
		this.end = end;
		this.active = active;
		setStatus(status);
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

	public Status getStatus() {
		return Status.parse(this.status);
	}

	public void setStatus(Status status) {
		this.status = status.getValue();
	}
}
