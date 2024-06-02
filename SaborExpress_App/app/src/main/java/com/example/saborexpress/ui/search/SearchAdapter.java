package com.example.saborexpress.ui.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saborexpress.R;
import com.example.saborexpress.RecipeActivity;
import com.example.saborexpress.recycler.RecyclerItems;
import com.example.saborexpress.recycler.Util;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    // Lista de elementos para el RecyclerView y referencia al fragmento
    private List<RecyclerItems> dataset;
    private Fragment fragment;

    // Constructor del adaptador
    public SearchAdapter(List<RecyclerItems> dataSet, Fragment fragment) {
        this.dataset = dataSet;
        this.fragment = fragment;
    }

    // Este método crea un nuevo ViewHolder cuando no hay suficientes ViewHolders para mostrar en pantalla
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout del item del RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_cell, parent, false);
        return new SearchViewHolder(view);
    }

    // Este otro método vincula los datos con el ViewHolder
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        // Obtenem los datos para la posición actual
        RecyclerItems dataForThisCell = dataset.get(position);
        // Mostramos los datos en el ViewHolder
        holder.showData(dataForThisCell);
    }

    // Este método devuelve el número de elementos en el dataset
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    // Este otro método actualiza la lista de elementos y notificar al adaptador que los datos han cambiado
    public void updateList(List<RecyclerItems> newList) {
        dataset = newList;
        notifyDataSetChanged();
    }

    // Clase interna que representa el ViewHolder para el RecyclerView
    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        // Declaramos las variables para los elementos de la vista
        private TextView recipe_name;
        private TextView description;
        private ImageView image_url;
        private Button cookButton;

        // Constructor del ViewHolder
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            // Encontramos los elementos de la vista por su ID
            recipe_name = itemView.findViewById(R.id.recipe_name);
            description = itemView.findViewById(R.id.description);
            image_url = itemView.findViewById(R.id.image_url);
            cookButton = itemView.findViewById(R.id.cookButton);
        }

        // Este método sirve para mostrar los datos en los elementos de la vista
        public void showData(RecyclerItems items) {
            // Establecemos el texto de los TextViews y la imagen del ImageView
            recipe_name.setText(items.getRecipe_Name());
            description.setText(items.getDescription());
            Util.downloadBitmapToImageView(items.getImage_url(), this.image_url);

            // Configuramos el listener para el botón "Cocinar"
            cookButton.setOnClickListener(v -> {
                // Obtenemos el contexto y crear un Intent para iniciar la actividad RecipeActivity
                Context context = itemView.getContext();
                Intent intent = new Intent(context, RecipeActivity.class);
                // Pasamos el objeto RecyclerItems directamente
                intent.putExtra("recipe", items);
                context.startActivity(intent);
            });
        }
    }
}
