package com.example.saborexpress.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.saborexpress.R;
import com.example.saborexpress.Server;

import org.json.JSONException;
import org.json.JSONObject;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText recipeNameEditText;
    private EditText descriptionEditText;
    private EditText foodTypeEditText;
    private EditText ingredientsEditText;
    private EditText stepsEditText;
    private EditText imageUrlEditText;
    private Button addRecipeButton;
    private String sessionToken;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipeNameEditText = findViewById(R.id.recipeNameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        foodTypeEditText = findViewById(R.id.foodTypeEditText);
        ingredientsEditText = findViewById(R.id.ingredientsEditText);
        stepsEditText = findViewById(R.id.stepsEditText);
        imageUrlEditText = findViewById(R.id.imageUrlEditText);
        addRecipeButton = findViewById(R.id.addRecipeButton);

        requestQueue = Volley.newRequestQueue(this);
        sessionToken = getIntent().getStringExtra("VALID_TOKEN");

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });
    }

    private void addRecipe() {
        String url = Server.name + "/add_recipe";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("recipe_name", recipeNameEditText.getText().toString());
            requestBody.put("description", descriptionEditText.getText().toString());
            requestBody.put("food_type", foodTypeEditText.getText().toString());
            requestBody.put("ingredients", ingredientsEditText.getText().toString());
            requestBody.put("steps", stepsEditText.getText().toString());
            requestBody.put("image_url", imageUrlEditText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(AddRecipeActivity.this, "Recipe added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(AddRecipeActivity.this, "Error adding recipe", Toast.LENGTH_SHORT).show();
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
}
