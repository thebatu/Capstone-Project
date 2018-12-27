package com.example.bats.homefoodie.database.dishDatabase;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.example.bats.homefoodie.database.userDatabase.UserEntry;

import java.util.List;

@Entity(tableName = "dish", foreignKeys = @ForeignKey(entity = UserEntry.class,
                                                      parentColumns = "id",
                                                      childColumns = "userId",
                                                      onDelete = ForeignKey.CASCADE),
                                                      indices = {@Index("userId")})
public class DishEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private String kitchen_name;
    private String name;
    private int price;
    private String userId;

    @Ignore
    private List<Ingredient> ingredientList;

    @Ignore
    public DishEntry(String userId, String name, int price, String description, String kitchen_name) {
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.kitchen_name = kitchen_name;
    }

    //constructor used by Room
    public DishEntry(int id, String userId, String name, int price, String description, String kitchen_name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.kitchen_name = kitchen_name;
    }

    //empty constructor for reflection operation
    @Ignore
    public DishEntry(){}




    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getKitchen_name() {
        return kitchen_name;
    }

    public void setKitchen_name(String kitchen_name) {
        this.kitchen_name = kitchen_name;
    }
}
