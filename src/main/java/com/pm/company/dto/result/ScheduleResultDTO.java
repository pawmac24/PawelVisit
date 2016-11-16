package com.pm.company.dto.result;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by pmackiewicz on 2015-10-21.
 */
public class ScheduleResultDTO {

    private Long scheduleId;

    private Date start;

    private Date stop;

    private String place;

    private Long roomId;

    private Long employeeId;

    public ScheduleResultDTO() {
		super();
	}

	public ScheduleResultDTO(Long scheduleId, Date start, Date stop, String place) {
		super();
		this.scheduleId = scheduleId;
		this.start = start;
		this.stop = stop;
		this.place = place;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
