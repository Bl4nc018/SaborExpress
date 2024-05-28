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
import com.example.saborexpress.recycler.RecyclerItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_item);
        searchView = view.findViewById(R.id.search_view);
        spinnerFoodType = view.findViewById(R.id.spinner_food_type);

        recipeList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new SearchAdapter(filteredList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(getContext());

        setupSearchView();
        setupSpinner();

        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    fetchRecipes(query, spinnerFoodType.getSelectedItem().toString());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    fetchRecipes(newText, spinnerFoodType.getSelectedItem().toString());
                }
                return false;
            }
        });
    }

    private void setupSpinner() {
        spinnerFoodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String query = searchView.getQuery().toString();
                if (!TextUtils.isEmpty(query)) {
                    fetchRecipes(query, parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String query = searchView.getQuery().toString();
                if (!TextUtils.isEmpty(query)) {
                    fetchRecipes(query, "");
                }
            }
        });
    }

    private void fetchRecipes(String query, String foodType) {
        String url = "http://10.0.2.2:8000/search_recipes?name=" + query;
        if (!TextUtils.isEmpty(foodType) && !foodType.equals("Todos")) {
            url += "&food_type=" + foodType;
        }

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        recipeList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject recipe = response.getJSONObject(i);
                                RecyclerItems item = new RecyclerItems(recipe);
                                recipeList.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        filterRecipes(query, foodType);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(request);
    }

    private void filterRecipes(String query, String foodType) {
        filteredList.clear();

        for (RecyclerItems item : recipeList) {
            boolean matchesQuery = TextUtils.isEmpty(query) || item.getRecipe_Name().toLowerCase().contains(query.toLowerCase());
            boolean matchesFoodType = TextUtils.isEmpty(foodType) || foodType.equals("Todos") || item.getFood_type().equalsIgnoreCase(foodType);

            if (matchesQuery && (foodType.equals("Todos") || matchesFoodType)) {
                filteredList.add(item);
            }
        }

        adapter.updateList(filteredList);
    }
}
