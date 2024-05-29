package com.example.saborexpress.recycler;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

// Clase que representa un elemento en el RecyclerView
public class RecyclerItems implements Serializable {

    // Variables para almacenar los datos de cada elemento
    private String recipe_name;
    private String description;
    private String food_type;
    private String ingredients;
    private String steps;
    private String image_url;
    private String author;  // Añadido: Nombre del autor

    // Métodos getter para cada variable
    public String getRecipe_Name() { return recipe_name; }
    public String getDescription() { return description; }
    public String getFood_type() { return food_type; }
    public String getIngredients() { return ingredients; }
    public String getSteps() { return steps; }
    public String getImage_url() { return image_url; }
    public String getAuthor() { return author; }  // Añadido: Getter para el nombre del autor

    // Constructor que toma los datos como parámetros
    public RecyclerItems(String recipe_name, String description, String food_type,
                         String ingredients, String steps, String image_url, String author) {
        this.recipe_name = recipe_name;
        this.description = description;
        this.food_type = food_type;
        this.ingredients = ingredients;
        this.steps = steps;
        this.image_url = image_url;
        this.author = author;  // Añadido: Asignar el nombre del autor
    }

    // Constructor que toma un objeto JSON y extrae los datos
    public RecyclerItems(JSONObject json) {
        try {
            this.recipe_name = json.getString("recipe_name");
            this.description = json.getString("description");
            this.food_type = json.getString("food_type");
            this.ingredients = json.getString("ingredients");
            this.steps = json.getString("steps");
            this.image_url = json.getString("image_url");
            this.author = json.getString("author");  // Añadido: Extraer el nombre del autor del JSON
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
