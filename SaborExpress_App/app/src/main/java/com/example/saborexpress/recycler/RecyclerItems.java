package com.example.saborexpress.recycler;

import org.json.JSONException;
import org.json.JSONObject;

// Clase que representa un elemento en el RecyclerView
public class RecyclerItems {

    // Variables para almacenar los datos de cada elemento
    private String recipe_name;


    // Métodos getter para cada variable
    public String getRecipe_Name() { return recipe_name; }

    // Constructor que toma los datos como parámetros
    public RecyclerItems(String place_name){
        this.recipe_name=place_name;
    }

    // Constructor que toma un objeto JSON y extrae los datos
    public RecyclerItems(JSONObject json){
        try{
            this.recipe_name = json.getString("recipe_name");
        }catch (JSONException e){ e.printStackTrace(); }
    }
}