package com.example.saborexpress;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.saborexpress.recycler.RecyclerItems;
import com.example.saborexpress.recycler.Util;

public class RecipeActivity extends AppCompatActivity {

    // Declaramos de variables para las vistas
    private TextView recipe_name;
    private TextView food_type;
    private TextView ingredients;
    private TextView steps;
    private ImageView image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establecemos el layout de la actividad
        setContentView(R.layout.activity_recipe);

        // Inicializamos las vistas utilizando sus IDs del layout
        recipe_name = findViewById(R.id.recipe_name);
        food_type = findViewById(R.id.food_type);
        ingredients = findViewById(R.id.ingredients);
        steps = findViewById(R.id.steps);
        image_url = findViewById(R.id.image_url);

        // Obtenemos el objeto RecipeItems pasado desde la actividad anterior
        RecyclerItems recipe = (RecyclerItems) getIntent().getSerializableExtra("recipe");

        // Si el objeto recipe no es nulo, establecemos los datos en las vistas correspondientes
        if (recipe != null) {
            recipe_name.setText(recipe.getRecipe_Name());
            food_type.setText(recipe.getFood_type());
            ingredients.setText(recipe.getIngredients());
            steps.setText(recipe.getSteps());
            // Descargamos y establecemos la imagen utilizando una utilidad
            Util.downloadBitmapToImageView(recipe.getImage_url(), this.image_url);
        }
    }
}