package com.pm.company.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by pmackiewicz on 2015-11-05.
 */
public class RoomUpdateDTO {

    @NotNull
    private Long roomId;

    @Size(min = 1, max = 5)
    private String number;

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

    @Override
    public String toString() {
        return "RoomUpdateDTO{" +
                "roomId=" + roomId +
                ", number='" + number + '\'' +
                '}';
    }
}
