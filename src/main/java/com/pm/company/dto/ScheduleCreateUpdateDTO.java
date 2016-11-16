package com.pm.company.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ScheduleCreateUpdateDTO {

	private Long scheduleId;

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

	public ScheduleCreateUpdateDTO() {
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
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

	@Override
	public String toString() {
		return "ScheduleCreateUpdateDTO{" +
				"scheduleId=" + scheduleId +
				", start=" + start +
				", stop=" + stop +
				", place='" + place + '\'' +
				", employeeId=" + employeeId +
				", roomId=" + roomId +
				'}';
	}
}
