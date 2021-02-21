package com.udacity.jdnd.course3.critterdatalayer.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_skill",
               joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"),
               uniqueConstraints = @UniqueConstraint(columnNames = { "employee_id", "skill_id" }))
    private List<Skill> skills;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_days_available",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "day_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = { "employee_id", "day_id" }))
    private List<WeekDay> daysAvailable;

    public Employee() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<WeekDay> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(List<WeekDay> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
