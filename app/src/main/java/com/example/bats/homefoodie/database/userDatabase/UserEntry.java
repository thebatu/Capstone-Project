package com.example.bats.homefoodie.database.userDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user", indices = {@Index("id")})
public class UserEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    //private String email;
    private String address;
    private boolean isSeller;
    private String businessName;

    /**
     * This constructor is used by HomeFoodieJsonParser. When the network fetch has JSON data, it
     * converts this data to UserEntry objects using this constructor.
     * @param name join date
     * @param email email address
     * @param address address where the food is cooked or bought
     * @param isSeller is a isSeller or just a buyer
     * @param businessName if isSeller then business name
     */
    @Ignore
    public UserEntry(String name,  String address, boolean isSeller, String businessName) {
        this.name = name;
       // this.email = email;
        this.address = address;
        this.businessName = businessName;
        this.isSeller = isSeller;
    }
    // Constructor used by Room to create WeatherEntries
    public UserEntry(int id, String name, String address, boolean isSeller, String businessName) {
        this.id = id;
        this.name = name;
       // this.email = email;
        this.address = address;
        this.businessName = businessName;
        this.isSeller = isSeller;
    }

    @Ignore
    public UserEntry(){}



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        this.isSeller = seller;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


}
