package com.example.saborexpress.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saborexpress.R;

// Clase que representa un ViewHolder para el RecyclerView
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    // Variables para los elementos de la vista
    private TextView recipe_name;
    private TextView description;
    private TextView food_type;
    private TextView ingredients;
    private TextView steps;
    private ImageView image_url;

    // Constructor del ViewHolder
    public RecyclerViewHolder(@NonNull View ivi){
        super(ivi);
        // Encontrar los elementos de la vista
        recipe_name = ivi.findViewById(R.id.recipe_name);
        description = ivi.findViewById(R.id.description);
//        food_type = ivi.findViewById(R.id.food_type);
//        ingredients = ivi.findViewById(R.id.ingredients);
//        steps = ivi.findViewById(R.id.steps);
//        image_url = ivi.findViewById(R.id.image_url);
    }

    // Método para mostrar los datos en los elementos de la vista
    public void showData(RecyclerItems items){
        // Establecer el texto de los TextViews y la imagen del ImageView
        recipe_name.setText(items.getRecipe_Name());
        description.setText(items.getDescription());
        food_type.setText(items.getFood_type());
        ingredients.setText(items.getIngredients());
        steps.setText(items.getSteps());
//        Util.downloadBitmapToImageView(items.getImage_url(), this.imageView);
    }
}