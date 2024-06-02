package com.example.saborexpress.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.saborexpress.LoginActivity;
import com.example.saborexpress.R;
import com.example.saborexpress.Server;
import com.example.saborexpress.recycler.RecyclerAdapter;
import com.example.saborexpress.recycler.RecyclerItems;
import com.example.saborexpress.recycler.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    // Declaramos de variables para los elementos de la interfaz de usuario
    private ImageView profileImageView;
    private TextView usernameTextView;
    private TextView favoriteFoodTypeTextView;
    private Button logoutButton;
    private Button addRecipeButton;
    private RecyclerView recipesRecyclerView;

    // Adaptador y lista para el RecyclerView
    private RecyclerAdapter recyclerAdapter;
    private List<RecyclerItems> recipesList;

    // Cola de solicitudes de Volley
    private RequestQueue requestQueue;
    private String sessionToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inicializamos los elementos de la interfaz de usuario
        profileImageView = view.findViewById(R.id.profileImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        favoriteFoodTypeTextView = view.findViewById(R.id.favoriteFoodTypeTextView);
        logoutButton = view.findViewById(R.id.logoutButton);
        addRecipeButton = view.findViewById(R.id.addRecipeButton);
        recipesRecyclerView = view.findViewById(R.id.recipesRecyclerView);

        // Inicializamos la cola de solicitudes de Volley
        requestQueue = Volley.newRequestQueue(requireContext());

        // Obtenemos el token de sesión pasado desde MainActivity
        sessionToken = getActivity().getIntent().getStringExtra("VALID_TOKEN");

        // Inicializamos la lista de recetas y el adaptador del RecyclerView
        recipesList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(recipesList, this);

        // Configuramos el RecyclerView con un LinearLayoutManager y el adaptador
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipesRecyclerView.setAdapter(recyclerAdapter);

        // Llamamos a los métodos para obtener el perfil del usuario y las recetas
        fetchUserProfile();
        fetchUserRecipes();

        // Configuramos del listener para el botón de cerrar sesión
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manejamos la lógica de cerrar sesión aquí
                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                // Redirigimos la pantalla a la de inicio de sesión
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Configuramos el listener para el botón de agregar receta
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad AddRecipeActivity pasando el token de sesión
                Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
                intent.putExtra("VALID_TOKEN", sessionToken); // Pasar el token de sesión
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Volvemos a cargar las recetas cuando el fragmento se reanuda
        fetchUserRecipes();
    }

    // Este método sirve para obtener el perfil del usuario
    private void fetchUserProfile() {
        String url = Server.name + "/user";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtenemos los datos del usuario del JSON de respuesta
                            String username = response.getString("username");
                            String favoriteFoodType = response.getString("foodtype");
                            String imageUrl = response.getString("image");

                            // Establecemos los valores en los elementos de la interfaz de usuario
                            usernameTextView.setText(username);
                            favoriteFoodTypeTextView.setText(favoriteFoodType);

                            // Cargamos la imagen usando la clase Util
                            Util.downloadBitmapToImageView(imageUrl, profileImageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Mostramos un mensaje de error si ocurre una excepción
                            Toast.makeText(getActivity(), "Error parsing user data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Mostramos un mensaje de error si la solicitud falla
                        Toast.makeText(getActivity(), "Error fetching user data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                // Añadimos el token de sesión a los encabezados de la solicitud
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("SessionToken", sessionToken);
                return headers;
            }
        };

        // Añadimos la solicitud a la cola de solicitudes de Volley
        requestQueue.add(jsonObjectRequest);
    }

    // El siguiente método es empleado para obtener las recetas del usuario
    private void fetchUserRecipes() {
        String url = Server.name + "/user_recipes";  // Asegúrate de que este endpoint exista y funcione

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // Limpiamos la lista de recetas antes de agregar nuevas
                        recipesList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                // Obtenemos los datos de cada receta del JSON de respuesta
                                JSONObject jsonObject = response.getJSONObject(i);
                                RecyclerItems recipe = new RecyclerItems(jsonObject);
                                recipesList.add(recipe);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // Notificamos al adaptador que los datos han cambiado
                        recyclerAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Mostramos un mensaje de error si la solicitud falla
                        Toast.makeText(getActivity(), "Error fetching recipes", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                // Añadimos el token de sesión a los encabezados de la solicitud
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("SessionToken", sessionToken);
                return headers;
            }
        };

        // Añadimos la solicitud a la cola de solicitudes de Volley
        requestQueue.add(jsonArrayRequest);
    }
}
