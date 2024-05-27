package com.example.saborexpress.ui.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.saborexpress.R;
import com.example.saborexpress.recycler.RecyclerAdapter;
import com.example.saborexpress.recycler.RecyclerItems;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<RecyclerItems> recipeList;
    private List<RecyclerItems> filteredList;
    private SearchView searchView;
    private Spinner spinnerFoodType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_item);
        searchView = view.findViewById(R.id.search_view);
        spinnerFoodType = view.findViewById(R.id.spinner_food_type);

        // Initialize the recipe list
        recipeList = new ArrayList<>();
        // Add your recipes to the list
        // recipeList.add(new RecyclerItems("Recipe 1", "Description 1", "Italian", "Ingredients 1", "Steps 1", "ImageURL"));
        // recipeList.add(new RecyclerItems("Recipe 2", "Description 2", "Mexican", "Ingredients 2", "Steps 2", "ImageURL"));

        filteredList = new ArrayList<>(recipeList);

        adapter = new RecyclerAdapter(filteredList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        setupSearchView();
        setupSpinner();

        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterRecipes(query, spinnerFoodType.getSelectedItem().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes(newText, spinnerFoodType.getSelectedItem().toString());
                return false;
            }
        });
    }

    private void setupSpinner() {
        spinnerFoodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterRecipes(searchView.getQuery().toString(), parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterRecipes(searchView.getQuery().toString(), "");
            }
        });
    }

    private void filterRecipes(String query, String foodType) {
        filteredList.clear();

        for (RecyclerItems item : recipeList) {
            boolean matchesQuery = TextUtils.isEmpty(query) || item.getRecipe_Name().toLowerCase().contains(query.toLowerCase());
            boolean matchesFoodType = TextUtils.isEmpty(foodType) || foodType.equals("All") || item.getFood_type().equalsIgnoreCase(foodType);

            if (matchesQuery && matchesFoodType) {
                filteredList.add(item);
            }
        }

        adapter.updateList(filteredList);
    }
}
