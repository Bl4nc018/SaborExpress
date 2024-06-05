package com.example.saborexpress.ui.profile;

import android.os.Bundle;
import android.util.Log;
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

    // Declaramos variables para los elementos de la interfaz de usuario
    private EditText recipeNameEditText;
    private EditText descriptionEditText;
    private EditText foodTypeEditText;
    private EditText ingredientsEditText;
    private EditText stepsEditText;
    private EditText imageUrlEditText;
    private Button addRecipeButton;
    private String sessionToken;

    // Declaramos la cola de solicitudes de Volley
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establecemos el layout para la actividad
        setContentView(R.layout.activity_add_recipe);

        // Inicializamos los elementos de la interfaz de usuario
        recipeNameEditText = findViewById(R.id.recipeNameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        foodTypeEditText = findViewById(R.id.foodTypeEditText);
        ingredientsEditText = findViewById(R.id.ingredientsEditText);
        stepsEditText = findViewById(R.id.stepsEditText);
        imageUrlEditText = findViewById(R.id.imageUrlEditText);
        addRecipeButton = findViewById(R.id.addRecipeButton);

        // Inicializamos la cola de solicitudes de Volley
        requestQueue = Volley.newRequestQueue(this);
        // Obtenemos el token de sesión de los extras del intent
        sessionToken = getIntent().getStringExtra("VALID_TOKEN");

        // Configuramos el listener para el botón "addRecipeButton"
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validamos si los campos requeridos están completos
                if (validateFields()) {
                    // Llamamos al método para agregar la receta cuando se haga clic en el botón
                    addRecipe();
                } else {
                    // Mostramos un mensaje si los campos requeridos no están completos
                    Toast.makeText(AddRecipeActivity.this, "Por favor, rellena todos los campos obligatorios.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Método para validar que todos los campos requeridos están completos
    private boolean validateFields() {
        return !recipeNameEditText.getText().toString().trim().isEmpty()
                && !descriptionEditText.getText().toString().trim().isEmpty()
                && !foodTypeEditText.getText().toString().trim().isEmpty()
                && !ingredientsEditText.getText().toString().trim().isEmpty()
                && !stepsEditText.getText().toString().trim().isEmpty();
    }

    // Este es el método para agregar una receta
    private void addRecipe() {
        // Definimos la URL del endpoint para agregar recetas
        String url = Server.name + "/add_recipe";

        // Creamos un objeto JSON para el cuerpo de la solicitud
        JSONObject requestBody = new JSONObject();
        try {
            // Ponemos los valores de los EditText en el objeto JSON
            requestBody.put("recipe_name", recipeNameEditText.getText().toString());
            requestBody.put("description", descriptionEditText.getText().toString());
            requestBody.put("food_type", foodTypeEditText.getText().toString());
            requestBody.put("ingredients", ingredientsEditText.getText().toString());
            requestBody.put("steps", stepsEditText.getText().toString());
            requestBody.put("image_url", imageUrlEditText.getText().toString());
        } catch (JSONException e) {
            // Manejamos excepción de JSON
            e.printStackTrace();
            return;
        }

        // Registramos el token de sesión en el log para depuración
        Log.d("AddRecipeActivity", "Session Token: " + sessionToken);

        // Creamos una solicitud JSON utilizando Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    // En la siguiente parte definiremos el método que se llama cuando se recibe una respuesta exitosa
                    @Override
                    public void onResponse(JSONObject response) {
                        // Mostramos un mensaje de éxito y finalizar la actividad
                        Toast.makeText(AddRecipeActivity.this, "Receta añadida exitosamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {

                    // Aquí definiremos el método al que se llama cuando ocurre un error en la solicitud
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error y mostrar un mensaje de error
                        error.printStackTrace();
                        Toast.makeText(AddRecipeActivity.this, "Error al añadir la receta", Toast.LENGTH_SHORT).show();
                    }
                }) {
            // Este método agrega encabezados a la solicitud
            @Override
            public java.util.Map<String, String> getHeaders() {
                // Creamos un mapa de encabezados y agregar el token de sesión
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("SessionToken", sessionToken);
                return headers;
            }
        };

        // Agregamos la solicitud a la cola de solicitudes de Volley
        requestQueue.add(jsonObjectRequest);
    }
}
