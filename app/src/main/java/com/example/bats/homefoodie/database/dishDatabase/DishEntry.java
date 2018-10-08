package com.example.bats.homefoodie.database.dishDatabase;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import com.example.bats.homefoodie.database.userDatabase.UserEntry;

@Entity(tableName = "dish", foreignKeys = @ForeignKey(entity = UserEntry.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index("id")})

public class DishEntry {

    private int id;
    private int userId;

    private String name;
    private int price;



}
