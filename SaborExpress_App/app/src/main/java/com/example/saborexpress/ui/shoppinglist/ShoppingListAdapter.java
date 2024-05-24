package com.example.saborexpress.ui.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saborexpress.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private List<ShoppingItem> shoppingItems;

    public ShoppingListAdapter(List<com.example.saborexpress.ui.shoppinglist.ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    public void addItem(ShoppingItem item) {
        shoppingItems.add(item);
        notifyItemInserted(shoppingItems.size() - 1);
    }

    public void toggleItemChecked(int position) {
        ShoppingItem item = shoppingItems.get(position);
        item.setChecked(!item.isChecked());
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingItem item = shoppingItems.get(position);
        holder.textViewItemName.setText(item.getName());
        holder.checkBoxItem.setChecked(item.isChecked());

        holder.checkBoxItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setChecked(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItemName;
        public CheckBox checkBoxItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            checkBoxItem = itemView.findViewById(R.id.checkBoxItem);

            itemView.setOnClickListener(v -> toggleItemChecked(getAdapterPosition()));
        }
    }
}
