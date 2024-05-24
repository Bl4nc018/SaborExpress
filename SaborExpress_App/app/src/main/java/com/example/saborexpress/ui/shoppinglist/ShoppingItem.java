package com.example.saborexpress.ui.shoppinglist;

public class ShoppingItem {

    private String name;
    private boolean isChecked;

    public ShoppingItem(String name) {
        this.name = name;
        this.isChecked = false;
    }

    public String getName() { return name; }

    public boolean isChecked() { return isChecked; }

    public void setChecked(boolean checked) { isChecked = checked; }
}
