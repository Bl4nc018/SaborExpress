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

// Fragmento que maneja la lista de compras
public class ShoppingListFragment extends Fragment {

    // Declaramos las variables para el RecyclerView, adaptador, lista de ítems, EditText y botón
    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private List<ShoppingItem> shoppingItems;
    private EditText editTextItem;
    private Button buttonAddItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        // Inicializamos las vistas
        recyclerView = view.findViewById(R.id.recyclerViewItems);
        editTextItem = view.findViewById(R.id.editTextItem);
        buttonAddItem = view.findViewById(R.id.buttonAddItem);

        // Inicializamos la lista de ítems y el adaptador
        shoppingItems = new ArrayList<>();
        adapter = new ShoppingListAdapter(shoppingItems);

        // Configuramos el RecyclerView con un LinearLayoutManager y el adaptador
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Configuramos el listener para el botón "Agregar ítem"
        buttonAddItem.setOnClickListener(v -> {
            // Obtenemos el nombre del ítem del EditText
            String itemName = editTextItem.getText().toString().trim();
            // Verificamos que el nombre no esté vacío
            if (!itemName.isEmpty()) {
                // Creamos un nuevo ítem y agregarlo a la lista a través del adaptador
                ShoppingItem newItem = new ShoppingItem(itemName);
                adapter.addItem(newItem);
                // Limpiamos el EditText
                editTextItem.setText("");
            }
        });

        return view;
    }
}
