package com.example.saborexpress.ui.shoppinglist;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saborexpress.R;

import java.util.List;

// Este es el adaptador para el RecyclerView que maneja la lista de compras
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    // Esta es la lista de ítems de la lista de compras
    private List<ShoppingItem> shoppingItems;

    // Este constructor del adaptador es el que inicializa la lista de ítems
    public ShoppingListAdapter(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    // Este es el método para agregar un ítem a la lista
    public void addItem(ShoppingItem item) {
        shoppingItems.add(item);
        notifyItemInserted(shoppingItems.size() - 1);
    }

    // Este es el método para remover un ítem de la lista
    public void removeItem(int position) {
        shoppingItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, shoppingItems.size());
    }

    // Este es el método que crea un nuevo ViewHolder cuando
    // no hay suficientes para mostrar en pantalla.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout del ítem de la lista de compras
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_list, parent, false);
        return new ViewHolder(view);
    }

    // Este es el método que vincula los datos con el ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtenemos los datos para la posición actual
        ShoppingItem item = shoppingItems.get(position);
        // Establecemos el nombre del ítem y el estado del CheckBox
        holder.textViewItemName.setText(item.getName());
        holder.checkBoxItem.setChecked(item.isChecked());
        holder.setItemStrikeThrough(item.isChecked());

        // Configuramos el listener para el cambio de estado del CheckBox
        holder.checkBoxItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                removeItem(holder.getAdapterPosition());
            }
        });

        // Configuramos el listener para el clic en el nombre del ítem
        holder.textViewItemName.setOnClickListener(v -> {
            item.setChecked(!item.isChecked());
            holder.setItemStrikeThrough(item.isChecked());
        });
    }

    // Este es el método que devuelve el número de elementos en la lista
    @Override
    public int getItemCount() { return shoppingItems.size(); }

    // Esta clase interna representa el ViewHolder para el RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Declaramos de variables para los elementos de la vista
        public TextView textViewItemName;
        public CheckBox checkBoxItem;

        // Este constructor del ViewHolder que inicializa los elementos de la vista
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            checkBoxItem = itemView.findViewById(R.id.checkBoxItem);
        }

        // Este método establece o quita el efecto de tachado en el nombre del ítem
        public void setItemStrikeThrough(boolean isChecked) {
            if (isChecked) {
                textViewItemName.setPaintFlags(textViewItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                textViewItemName.setPaintFlags(textViewItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
        }
    }
}
