package com.example.saborexpress.recycler;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

// Clase que representa un elemento en el RecyclerView
public class RecyclerItems {

    // Variables para almacenar los datos de cada elemento
    private String recipe_name;
    private String description;
    private String food_type;
    private String ingredients;
    private String steps;
    private String image_url;


    // Métodos getter para cada variable
    public String getRecipe_Name() { return recipe_name; }
    public String getDescription() { return description; }
    public String getFood_type() { return food_type; }
    public String getIngredients() { return ingredients; }
    public String getSteps() { return steps; }
    public String getImage_url() { return image_url; }

    // Constructor que toma los datos como parámetros
    public RecyclerItems(String recipe_name, String description, String food_type,
                         String ingredients, String steps, String image_url){
        this.recipe_name=recipe_name;
        this.description=description;
        this.food_type=food_type;
        this.ingredients=ingredients;
        this.steps=steps;
        this.image_url=image_url;
    }

    // Constructor que toma un objeto JSON y extrae los datos
    public RecyclerItems(JSONObject json){
        try{
            this.recipe_name = json.getString("recipe_name");
            this.description = json.getString("description");
            this.food_type = json.getString("food_type");
            this.ingredients = json.getString("ingredients");
            this.steps = json.getString("steps");
            this.image_url = json.getString("image_url");
        }catch (JSONException e){ e.printStackTrace(); }
    }



}