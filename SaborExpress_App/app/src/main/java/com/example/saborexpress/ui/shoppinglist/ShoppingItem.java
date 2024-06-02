package com.example.saborexpress.ui.shoppinglist;

// Clase que representa un ítem en la lista de compras
public class ShoppingItem {

    // Declaramos de variables para el nombre del ítem y si está marcado o no
    private String name;
    private boolean isChecked;

    // Este constructor inicializa el nombre del ítem y establece isChecked como falso por defecto
    public ShoppingItem(String name) {
        this.name = name;
        this.isChecked = false;
    }

    // Este es el método getter para obtener el nombre del ítem
    public String getName() { return name; }

    // Este es el método getter para verificar si el ítem está marcado
    public boolean isChecked() { return isChecked; }

    // Este es el método setter para establecer si el ítem está marcado
    public void setChecked(boolean checked) { isChecked = checked; }
}
