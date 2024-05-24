package com.example.saborexpress.ui.shoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saborexpress.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private List<ShoppingItem> shoppingItems;
    private EditText editTextItem;
    private Button buttonAddItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewItems);
        editTextItem = view.findViewById(R.id.editTextItem);
        buttonAddItem = view.findViewById(R.id.buttonAddItem);

        shoppingItems = new ArrayList<>();
        adapter = new ShoppingListAdapter(shoppingItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        buttonAddItem.setOnClickListener(v -> {
            String itemName = editTextItem.getText().toString().trim();
            if (!itemName.isEmpty()) {
                ShoppingItem newItem = new ShoppingItem(itemName);
                adapter.addItem(newItem);
                editTextItem.setText("");
            }
        });

        return view;
    }
}
