package com.example.saborexpress;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.saborexpress.recycler.RecyclerItems;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        RecyclerItems recipe = (RecyclerItems) getIntent().getSerializableExtra("recipe");

        if (recipe != null) {
            // Usa los datos del objeto recipe para actualizar la UI
        }
    }
}
