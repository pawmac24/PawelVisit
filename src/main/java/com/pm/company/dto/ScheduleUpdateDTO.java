package com.pm.company.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ScheduleUpdateDTO {
	@NotNull
	private Date start;

	@NotNull
	private Date stop;

	@NotBlank
	@Size(min = 1, max = 100)
	private String place;

	public ScheduleUpdateDTO() {
	}

	public ScheduleUpdateDTO(Date start, Date stop, String place) {
		super();
		this.start = start;
		this.stop = stop;
		this.place = place;
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

}
