package com.ricardo.ricardo.models;

import java.util.List;

public class ResponseList {
    private String category;
    private List<ItemsCategory> items;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ItemsCategory> getItems() {
        return items;
    }

    public void setItems(List<ItemsCategory> items) {
        this.items = items;
    }
}
