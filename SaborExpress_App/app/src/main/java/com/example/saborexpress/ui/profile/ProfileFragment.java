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

    private ImageView profileImageView;
    private TextView usernameTextView;
    private TextView favoriteFoodTypeTextView;
    private Button logoutButton;
    private Button addRecipeButton;
    private RecyclerView recipesRecyclerView;

    private RecyclerAdapter recyclerAdapter;
    private List<RecyclerItems> recipesList;

    private RequestQueue requestQueue;
    private String sessionToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageView = view.findViewById(R.id.profileImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        favoriteFoodTypeTextView = view.findViewById(R.id.favoriteFoodTypeTextView);
        logoutButton = view.findViewById(R.id.logoutButton);
        addRecipeButton = view.findViewById(R.id.addRecipeButton);
        recipesRecyclerView = view.findViewById(R.id.recipesRecyclerView);

        requestQueue = Volley.newRequestQueue(requireContext());

        // Obtener el token de sesión pasado desde MainActivity
        sessionToken = getActivity().getIntent().getStringExtra("VALID_TOKEN");

        recipesList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(recipesList, this);

        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipesRecyclerView.setAdapter(recyclerAdapter);

        fetchUserProfile();
        fetchUserRecipes();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout logic here
                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                // Redirect to login screen
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
                intent.putExtra("VALID_TOKEN", sessionToken); // Pasa el token de sesión
                startActivity(intent);
            }
        });


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        fetchUserRecipes();  // Volver a cargar las recetas cuando el fragmento se reanuda
    }

    private void fetchUserProfile() {
        String url = Server.name + "/user";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String username = response.getString("username");
                            String favoriteFoodType = response.getString("foodtype");
                            String imageUrl = response.getString("image");

                            usernameTextView.setText(username);
                            favoriteFoodTypeTextView.setText(favoriteFoodType);

                            // Load the image using your Util class
                            Util.downloadBitmapToImageView(imageUrl, profileImageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error parsing user data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(), "Error fetching user data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("SessionToken", sessionToken);
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchUserRecipes() {
        String url = Server.name + "/user_recipes";  // Asegúrate de que este endpoint exista y funcione

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        recipesList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                RecyclerItems recipe = new RecyclerItems(jsonObject);
                                recipesList.add(recipe);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        recyclerAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(), "Error fetching recipes", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("SessionToken", sessionToken);
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }
}
