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
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        userFoodType = findViewById(R.id.userFoodType);
        userImage = findViewById(R.id.userImage);
        registerButton = findViewById(R.id.registerB);

        requestQueue = Volley.newRequestQueue(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String password = userPassword.getText().toString();
                String foodType = userFoodType.getText().toString();
                String image = userImage.getText().toString();
                registerUser(user, password, foodType, image);
            }
        });
    }

    private void registerUser(String userName, String userPassword, String userFoodType, String userImage) {
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

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Server.name + "/user",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error en el registro: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        this.requestQueue.add(request);
    }
}
