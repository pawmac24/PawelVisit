package com.pm.company.model;

/**
 * Created by pmackiewicz on 2015-09-22.
 */
public enum Status {
    NEW(0),
    ACCEPTED(1),
    CANCELLED(2),
    OLD(3);

    public static boolean isValid(int value) {
        for (Status itemStatus : Status.values()) {
            if (itemStatus.getValue() == value) {
                return true;
            }
        }
        return false;
    }

    private final int value;

    private Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Status parse(int value) {
        Status parsedStatus = null; // Default
        for (Status itemStatus : Status.values()) {
            if (itemStatus.getValue() == value) {
                parsedStatus = itemStatus;
                break;
            }
        }
        return parsedStatus;
    }
}
