package com.example.saborexpress.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.saborexpress.R;
import com.example.saborexpress.recycler.RecyclerAdapter;
import com.example.saborexpress.recycler.RecyclerItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView; // RecyclerView para mostrar la lista de elementos
    private RecyclerAdapter adapter; // Adaptador para el RecyclerView
    private List<RecyclerItems> homeRecipesList; // Lista de elementos para el RecyclerView
    private Fragment fragment = this;
    private RequestQueue requestQueue; // Cola de peticiones de Volley

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflamos el diseño del fragmento.
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Obtenemos la referencia del RecyclerView del diseño inflado.
        recyclerView = view.findViewById(R.id.recycler_view_item);
        // Inicializamos la lista de elementos.
        homeRecipesList = new ArrayList<>();
        // Inicializamos la cola de peticiones de Volley.
        requestQueue = Volley.newRequestQueue(getContext());

        // Llamamos al método para buscar recetas aleatorias.
        fetchRandomRecipes();

        return view;
    }

    // Método para buscar recetas aleatorias
    private void fetchRandomRecipes() {
        String url = "http://10.0.2.2:8000/random_recipes?num_recipes=10"; // URL del endpoint para obtener recetas aleatorias

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        homeRecipesList.clear(); // Limpiamos la lista actual para evitar duplicados
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject recipe = response.getJSONObject(i);
                                RecyclerItems data = new RecyclerItems(recipe);
                                homeRecipesList.add(data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // Creamos un adaptador con la lista de datos y la actividad asociada.
                        adapter = new RecyclerAdapter(homeRecipesList, fragment);
                        // Configuramos el RecyclerView con el adaptador y un LinearLayoutManager.
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Agregamos la solicitud a la cola de Volley para su procesamiento.
        requestQueue.add(request);
    }
}
