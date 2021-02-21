package com.udacity.jdnd.course3.critterdatalayer.entity;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
public class WeekDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @org.hibernate.annotations.Type(type = "org.hibernate.type.StringType")
    private String day;

    public WeekDay() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDay() {
        DayOfWeek currentDay = null;

        switch(day) {
            case "MONDAY" : currentDay = DayOfWeek.MONDAY;
                            break;
            case "TUESDAY" : currentDay = DayOfWeek.TUESDAY;
                break;
            case "WEDNESDAY" : currentDay = DayOfWeek.WEDNESDAY;
                break;
            case "THURSDAY" : currentDay = DayOfWeek.THURSDAY;
                break;
            case "FRIDAY" : currentDay = DayOfWeek.FRIDAY;
                break;
            case "SATURDAY" : currentDay = DayOfWeek.SATURDAY;
                break;
            case "SUNDAY" : currentDay = DayOfWeek.SUNDAY;
                break;
        }

        return currentDay;
    }

    public void setDay(DayOfWeek day) {
        this.day = day.toString();
    }
}
