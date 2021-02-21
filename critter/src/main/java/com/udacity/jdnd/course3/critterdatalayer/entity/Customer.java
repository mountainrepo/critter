package com.udacity.jdnd.course3.critterdatalayer.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @org.hibernate.annotations.Type(type = "org.hibernate.type.StringType")
    private String name;

    @org.hibernate.annotations.Type(type = "org.hibernate.type.StringType")
    private String phoneNumber;

    @org.hibernate.annotations.Type(type = "org.hibernate.type.StringType")
    private String notes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "customer_pet",
               joinColumns = @JoinColumn(name = "pet_id"),
               inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Pet> pets;

    public Customer() {

    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
