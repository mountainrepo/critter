package com.udacity.jdnd.course3.critterdatalayer.entity;

import javax.persistence.*;
import java.time.*;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @org.hibernate.annotations.Type(type = "org.hibernate.type.StringType")
    private String type;

    @org.hibernate.annotations.Type(type = "org.hibernate.type.StringType")
    private String name;

    private Long ownerId;

    private LocalDate birthDate;

    @org.hibernate.annotations.Type(type = "org.hibernate.type.StringType")
    private String notes;

    public Pet() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
