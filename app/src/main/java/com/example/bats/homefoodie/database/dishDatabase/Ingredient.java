package com.example.bats.homefoodie.database.dishDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ingredient", foreignKeys = @ForeignKey(entity = DishEntry.class,
                                                             parentColumns = "id",
                                                             childColumns = "dishId",
                                                             onDelete = ForeignKey.CASCADE),
                                                             indices = {@Index("dishId")})
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private int id;  // Ingredient id
    private int dishId;  // dish id
    private String quantity;
    private String name;

    @Ignore
    public Ingredient(int dishId, String name, String quantity) {
        this.dishId = dishId;
        this.name = name;
        this.quantity = quantity;
    }

    public Ingredient(int id, int dishId, String quantity, String name) {
        this.id = id;
        this.dishId = dishId;
        this.quantity = quantity;
        this.name = name;
    }


    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
