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

    private List<RecyclerItems> dataset;
    private Fragment fragment;

    public SearchAdapter(List<RecyclerItems> dataSet, Fragment fragment) {
        this.dataset = dataSet;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_cell, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        RecyclerItems dataForThisCell = dataset.get(position);
        holder.showData(dataForThisCell);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void updateList(List<RecyclerItems> newList) {
        dataset = newList;
        notifyDataSetChanged();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {

        private TextView recipe_name;
        private TextView description;
        private ImageView image_url;
        private Button cookButton;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_name = itemView.findViewById(R.id.recipe_name);
            description = itemView.findViewById(R.id.description);
            image_url = itemView.findViewById(R.id.image_url);
            cookButton = itemView.findViewById(R.id.cookButton);
        }

        public void showData(RecyclerItems items) {
            recipe_name.setText(items.getRecipe_Name());
            description.setText(items.getDescription());
            Util.downloadBitmapToImageView(items.getImage_url(), this.image_url);

            cookButton.setOnClickListener(v -> {
                Context context = itemView.getContext();
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra("recipe", items);  // Pasar el objeto RecyclerItems directamente
                context.startActivity(intent);
            });
        }
    }
}
