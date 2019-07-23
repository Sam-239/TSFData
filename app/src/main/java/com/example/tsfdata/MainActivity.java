package com.example.tsfdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class  MainActivity extends AppCompatActivity {

        private EditText etEmail, etPassword;
        private Button btnLogin;
        private RequestQueue queue;
        private Button button;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            button = findViewById(R.id.signUp);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openSignup();
                }
            });
            setupView();
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();

                }
            });
        }


        private void openSignup() {
            Intent intent = new Intent(this, Signup.class);
            startActivity(intent);
        }

        private void login() {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);

            JSONObject regParams = new JSONObject(params);

            String url = Constants.BASE_URL + Constants.LOGIN_URL;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, regParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        int id = data.getInt("id");
                        String email = data.getString("email");
                        Toast.makeText(MainActivity.this, "User logged in with id:" + id + "email:" + email, Toast.LENGTH_SHORT).show();
                        String ID= String.valueOf(id);
                        Intent Int = new Intent(MainActivity.this, Toggle.class);
                        Int.putExtra("IDNUMBER1",ID);
                        startActivity(Int);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", "Error logging in");
                    Toast.makeText(MainActivity.this, "Login error Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
        }


        private void setupView() {
            etEmail = findViewById(R.id.etEmail);
            etPassword = findViewById(R.id.etPassword);
            btnLogin = findViewById(R.id.btnLogin);
            queue = Volley.newRequestQueue(this);

        }
    }