package com.pm.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by pmackiewicz on 2015-11-04.
 */

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "number", length = 5)
    private String number;

    @ManyToOne
    @JoinColumn(name="department_id", nullable = false)
    @NotNull
    private Department department;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<Schedule> schedules;

    public Room() {
    }

    public Room(String number, Department department) {
        this.number = number;
        this.department = department;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getDepartmentId(){
        return this.department.getDepartmentId();
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", number='" + number + '\'' +
                '}';
    }
}
