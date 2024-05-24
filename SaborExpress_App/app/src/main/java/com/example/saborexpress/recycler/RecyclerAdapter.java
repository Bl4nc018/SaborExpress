package com.example.saborexpress.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saborexpress.R;

import java.util.List;

// Adaptador para el RecyclerView que muestra los elementos recomendados
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    // Lista de elementos recomendados y fragmento que contiene el RecyclerView
    private List<RecyclerItems> dataset;
    private Fragment fragment;

    // Constructor del adaptador
    public RecyclerAdapter(List<RecyclerItems> dataSet, Fragment fragment){
        this.dataset=dataSet;
        this.fragment=fragment;
    }

    // Método para crear un nuevo ViewHolder
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        // Inflar la vista de la celda del RecyclerView
        View recommendView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_cell, parent, false);
        // Crear y retornar un nuevo ViewHolder
        return new RecyclerViewHolder(recommendView);
    }

    // Método para vincular los datos con el ViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position){
        // Obtener los datos para esta celda
        RecyclerItems dataForThisCell = dataset.get(position);
        // Mostrar los datos en el ViewHolder
        holder.showData(dataForThisCell);
    }

    // Método para obtener el número de elementos en el dataset
    @Override
    public int getItemCount(){ return dataset.size(); }
}