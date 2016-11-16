package com.pm.company.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ScheduleCreateDTO {

	@NotNull
	private Date start;

	@NotNull
	private Date stop;

	@NotBlank
	@Size(min = 1, max = 100)
	private String place;

	@NotNull
	private Long employeeId;

	@NotNull
	private Long roomId;

	public ScheduleCreateDTO() {
	}

	public ScheduleCreateDTO(Date start, Date stop, String place, Long employeeId, Long roomId) {
		this.start = start;
		this.stop = stop;
		this.place = place;
		this.employeeId = employeeId;
		this.roomId = roomId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getStop() {
		return stop;
	}

	public void setStop(Date stop) {
		this.stop = stop;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
}
