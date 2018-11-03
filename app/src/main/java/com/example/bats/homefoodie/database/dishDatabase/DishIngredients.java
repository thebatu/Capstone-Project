package com.example.bats.homefoodie.database.dishDatabase;


public class DishIngredients {

    private String quantity;
    private String title;

    public DishIngredients(String title, String quantity) {
        this.title = title;
        this.quantity = quantity;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}





