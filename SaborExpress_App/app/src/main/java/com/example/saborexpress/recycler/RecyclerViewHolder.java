package com.example.saborexpress.recycler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saborexpress.R;

// Clase que representa un ViewHolder para el RecyclerView
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    // Variables para los elementos de la vista
    private TextView recipe_name;

    // Constructor del ViewHolder
    public RecyclerViewHolder(@NonNull View ivi){
        super(ivi);
        // Encontrar los elementos de la vista
//        recipe_name = ivi.findViewById(R.id.recipe_name);
    }

    // Método para mostrar los datos en los elementos de la vista
    public void showData(RecyclerItems items){
        // Establecer el texto de los TextViews y la imagen del ImageView
        recipe_name.setText(items.getRecipe_Name());
//        Util.downloadBitmapToImageView(items.getImage_url(), this.imageView);
    }
}