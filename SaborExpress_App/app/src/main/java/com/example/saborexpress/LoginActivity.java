package com.example.saborexpress;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    // Declaramos de variables para la cola de solicitudes, contexto, campos de texto y botones
    private RequestQueue requestQueue;
    private Context context = this;
    private EditText userName;
    private EditText userPassword;
    private Button loginButton;
    private TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializamos las vistas
        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.loginButton);
        registerText = findViewById(R.id.registerText);

        // Inicializamos la cola de solicitudes de Volley
        requestQueue = Volley.newRequestQueue(this);

        // Configuramos el listener para el botón de inicio de sesión
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos los valores de los campos de texto
                String user = userName.getText().toString();
                String password = userPassword.getText().toString();
                // Llamamos al método para iniciar sesión
                loginUser(user, password);
            }
        });

        // Configuramos el listener para el texto de registro
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciamos la actividad de registro
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // Este método para iniciar sesión
    private void loginUser(String userName, String userPassword) {
        // Creamos el cuerpo de la solicitud JSON
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", userName);
            requestBody.put("password", userPassword);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Creamos una solicitud JSON para el inicio de sesión
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Server.name + "/user/session",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtenemos el token de sesión de la respuesta
                            String receivedToken = response.getString("SessionToken");
                            Toast.makeText(context, "Token: " + receivedToken, Toast.LENGTH_SHORT).show();
                            // Iniciamos la actividad principal y pasar el usuario y el token
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("VALID_USER", userName);
                            intent.putExtra("VALID_TOKEN", receivedToken);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejamos errores en la respuesta de la solicitud
                        if (error.networkResponse == null) {
                            Toast.makeText(context, "No connection established", Toast.LENGTH_LONG).show();
                        } else {
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(context, "Response status " + serverCode, Toast.LENGTH_LONG).show();
                        }
                        error.printStackTrace();
                    }
                }
        );

        // Añadimos la solicitud a la cola de solicitudes de Volley
        this.requestQueue.add(request);
    }
}
