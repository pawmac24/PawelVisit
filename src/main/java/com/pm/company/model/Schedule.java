package com.pm.company.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "schedule")
public class Schedule {

	@Id
	@GeneratedValue
	@Column(name = "schedule_id")
	private Long scheduleId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start", nullable = false)
	@NotNull
	private Date start;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "stop", nullable = false)
	@NotNull
	private Date stop;
	
	@Column(name = "place", nullable = false, length = 100)
	@NotBlank
	@Size(min = 1, max = 100)
	private String place;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="employee_id", nullable = false, updatable = false)
	@NotNull
	private Employee employee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="room_id", nullable = false)
	@NotNull
	private Room room;

	public Schedule() {
		super();
	}

	public Schedule(Date start, Date stop, String place, Employee employee, Room room) {
		super();
		this.start = start;
		this.stop = stop;
		this.place = place;
		this.employee = employee;
		this.room = room;
	}

	public Schedule(Long scheduleId, Date start, Date stop, String place, Employee employee, Room room) {
		this(start, stop, place, employee, room);
		this.scheduleId = scheduleId;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
