package com.example.bats.homefoodie.database.dishDatabase;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.example.bats.homefoodie.database.userDatabase.UserEntry;

@Entity(tableName = "dish", foreignKeys = @ForeignKey(entity = UserEntry.class,
                                                      parentColumns = "id",
                                                      childColumns = "userId",
                                                      onDelete = ForeignKey.CASCADE),
                                                      indices = {@Index("userId")})
public class DishEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String name;
    private int price;
    @Embedded
    private DishIngredients dishIng;

    public DishEntry(int userId, String name, int price, DishIngredients dishIng) {
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.dishIng = dishIng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public DishIngredients getDishIng() {
        return dishIng;
    }

    public void setDishIng(DishIngredients dishIng) {
        this.dishIng = dishIng;
    }

}
