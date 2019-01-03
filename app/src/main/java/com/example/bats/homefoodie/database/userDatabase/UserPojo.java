package com.example.bats.homefoodie.database.userDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class UserPojo {

    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String address;
    Boolean isSeller;
    String businessName;

    public UserPojo(int id, String address, String businessName, Boolean isSeller, String name) {
        this.id = id;
        this.address = address;
        this.businessName = businessName;
        this.isSeller = isSeller;
        this.name = name;
    }

    public UserPojo() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Boolean getIsSeller() {
        return isSeller;
    }

    public void setIsSeller(Boolean isSeller) {
        this.isSeller = isSeller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}


