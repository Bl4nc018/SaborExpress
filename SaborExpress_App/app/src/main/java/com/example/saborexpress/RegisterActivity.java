package com.example.saborexpress;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    // Declaramos las variables para la cola de solicitudes, contexto y vistas
    private RequestQueue requestQueue;
    private Context context = this;
    private EditText userName;
    private EditText userPassword;
    private EditText userFoodType;
    private EditText userImage;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establecemos el layout de la actividad
        setContentView(R.layout.activity_register);

        // Inicializamos las vistas utilizando sus IDs del layout
        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        userFoodType = findViewById(R.id.userFoodType);
        userImage = findViewById(R.id.userImage);
        registerButton = findViewById(R.id.registerB);

        // Inicializamos la cola de solicitudes de Volley
        requestQueue = Volley.newRequestQueue(this);

        // Establecemos un listener para el botón de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos los valores de los campos de entrada
                String user = userName.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                String foodType = userFoodType.getText().toString().trim();
                String image = userImage.getText().toString().trim();

                // Validamos si los campos requeridos están completos
                if (user.isEmpty() || password.isEmpty() || foodType.isEmpty()) {
                    Toast.makeText(context, "Por favor, rellena todos los campos obligatorios.", Toast.LENGTH_LONG).show();
                } else {
                    // Llamamos al método para registrar el usuario
                    registerUser(user, password, foodType, image);
                }
            }
        });
    }

    // Este método sirve para registrar un nuevo usuario
    private void registerUser(String userName, String userPassword, String userFoodType, String userImage) {
        // Creamos un objeto JSON para enviar en la solicitud
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", userName);
            requestBody.put("password", userPassword);
            requestBody.put("foodtype", userFoodType);
            if (!userImage.isEmpty()) {
                requestBody.put("image", userImage);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Creamos una solicitud JSON para el registro del usuario
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Server.name + "/user",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Mostramos un mensaje de éxito y redirigir a la actividad de login
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Mostramos un mensaje de error en caso de fallo en el registro
                        Toast.makeText(context, "Error al realizar el registro. ", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Añadimos la solicitud a la cola de solicitudes de Volley
        this.requestQueue.add(request);
    }
}
