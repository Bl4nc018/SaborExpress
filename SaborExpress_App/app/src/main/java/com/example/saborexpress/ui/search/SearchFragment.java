package com.example.saborexpress.ui.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.saborexpress.R;
import com.example.saborexpress.Server;
import com.example.saborexpress.recycler.RecyclerItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    // Declaramos las variables para el RecyclerView, adaptador, listas y vistas
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<RecyclerItems> recipeList;
    private List<RecyclerItems> filteredList;
    private SearchView searchView;
    private Spinner spinnerFoodType;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Inicializamos las vistas y listas
        recyclerView = view.findViewById(R.id.recycler_view_item);
        searchView = view.findViewById(R.id.search_view);
        spinnerFoodType = view.findViewById(R.id.spinner_food_type);

        recipeList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new SearchAdapter(filteredList, this);

        // Configuramos el RecyclerView con un LinearLayoutManager y el adaptador
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Inicializamos la cola de solicitudes de Volley
        requestQueue = Volley.newRequestQueue(getContext());

        // Configuramos la vista de búsqueda y el spinner
        setupSearchView();
        setupSpinner();

        return view;
    }

    // En esta parte configuramos la vista de búsqueda
    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Si el texto no está vacío, realizamos la búsqueda de recetas
                if (!TextUtils.isEmpty(query)) {
                    fetchRecipes(query, spinnerFoodType.getSelectedItem().toString());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Si el texto no está vacío, realizamos la búsqueda de recetas
                if (!TextUtils.isEmpty(newText)) {
                    fetchRecipes(newText, spinnerFoodType.getSelectedItem().toString());
                }
                return false;
            }
        });
    }

    // Este método sirve para configurar el spinner
    private void setupSpinner() {
        spinnerFoodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Realizamos la búsqueda de recetas cuando se selecciona un elemento en el spinner
                String query = searchView.getQuery().toString();
                if (!TextUtils.isEmpty(query)) {
                    fetchRecipes(query, parent.getItemAtPosition(position).toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    // En este método obtenemos recetas desde el servidor
    private void fetchRecipes(String query, String foodType) {
        // Construímos la URL para la solicitud
        String url = Server.name + "/search_recipes?name=" + query;
        if (!TextUtils.isEmpty(foodType) && !foodType.equals("Todos")) {
            url += "&food_type=" + foodType;
        }

        // Creamos una solicitud JSON para obtener las recetas
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        recipeList.clear();
                        // Procesamos la respuesta y agregamos las recetas a la lista
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject recipe = response.getJSONObject(i);
                                RecyclerItems item = new RecyclerItems(recipe);
                                recipeList.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // Filtramos las recetas según la consulta y el tipo de comida
                        filterRecipes(query, foodType);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Mostramos un mensaje de error si la solicitud falla
                        Toast.makeText(getContext(), "Error en la solicitud. ", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Añadimos la solicitud a la cola de solicitudes de Volley
        requestQueue.add(request);
    }

    // Aquí se filtrarán las recetas según la consulta y el tipo de comida
    private void filterRecipes(String query, String foodType) {
        filteredList.clear();

        // Filtramos las recetas que coincidan con la consulta y el tipo de comida
        for (RecyclerItems item : recipeList) {
            boolean matchesQuery = TextUtils.isEmpty(query) || item.getRecipe_Name().toLowerCase().contains(query.toLowerCase());
            boolean matchesFoodType = TextUtils.isEmpty(foodType) || foodType.equals("Todos") || item.getFood_type().equalsIgnoreCase(foodType);

            if (matchesQuery && (foodType.equals("Todos") || matchesFoodType)) {
                filteredList.add(item);
            }
        }

        // Actualizamos la lista del adaptador para notificar los cambios
        adapter.updateList(filteredList);
    }
}
