package com.example.bobel.entity;

import javax.persistence.*;

import com.example.bobel.entity.User;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String breed;

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }



    public String getOwnerName() {
        return owner != null ? owner.getUsername() : "none";
    }

    public String getPetName() {
        return petName;
    }

    public User getOwner() {
        return owner;
    }


    public Pet(String petName, String breed, User user) {
        this.owner=user;
        this.petName = petName;
        this.breed = breed;
    }

    private String petName;

    public Pet() {

    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public void setPetName(String petName) {
        this.petName = petName;
    }
}
