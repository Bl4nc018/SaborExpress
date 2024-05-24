package com.example.saborexpress;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.saborexpress.recycler.RecyclerItems;
import com.example.saborexpress.recycler.Util;

public class RecipeActivity extends AppCompatActivity {

    private TextView recipe_name;
    private TextView food_type;
    private TextView ingredients;
    private TextView steps;
    private ImageView image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipe_name = findViewById(R.id.recipe_name);
        food_type = findViewById(R.id.food_type);
        ingredients = findViewById(R.id.ingredients);
        steps = findViewById(R.id.steps);
        image_url = findViewById(R.id.image_url);

        RecyclerItems recipe = (RecyclerItems) getIntent().getSerializableExtra("recipe");

        if (recipe != null) {
            recipe_name.setText(recipe.getRecipe_Name());
            food_type.setText(recipe.getFood_type());
            ingredients.setText(recipe.getIngredients());
            steps.setText(recipe.getSteps());
            Util.downloadBitmapToImageView(recipe.getImage_url(), this.image_url);
        }
    }
}
