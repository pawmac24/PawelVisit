package com.pm.company;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Created by pmackiewicz on 2016-01-25.
 */
public class MondaySunday
{
    public static void main(String[] args)
    {
        LocalDate today = LocalDate.now();
        int todayDayOfWeekValue = today.getDayOfWeek().getValue();

        LocalDate monday = today;
        if(todayDayOfWeekValue > DayOfWeek.MONDAY.getValue()){
            monday = monday.minusDays(todayDayOfWeekValue - DayOfWeek.MONDAY.getValue());
        }

        LocalDate sunday = today;
        if(todayDayOfWeekValue < DayOfWeek.SUNDAY.getValue()){
            sunday = sunday.plusDays(DayOfWeek.SUNDAY.getValue() - todayDayOfWeekValue);
        }

        System.out.println("Today: " + today);
        System.out.println("Monday of the Week: " + monday);
        System.out.println("Sunday of the Week: " + sunday);
    }
}