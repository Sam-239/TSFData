package com.example.tsfdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    private EditText userName, PassWord;
    private Button Signup;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        layout();
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });
    }
    /*private void openPersonalinfo()
    {
       Intent intent = new Intent(this,Personalinfo.class);
       startActivity(intent);
    }*/
    private void SignUp() {
        String email = userName.getText().toString();
        String password = PassWord.getText().toString();
        if (email.equals("") || password.equals("")) {
            Toast.makeText(Signup.this, "Signup error,Please fill in all the required fields", Toast.LENGTH_SHORT).show();

        } else {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);
            JSONObject regParams = new JSONObject(params);
            String url = Constants.BASE_URL + Constants.SIGNUP_URL;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, regParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        int id = data.getInt("id");
                        String URl = String.valueOf(id);
                        /*String URL = Constants.BASE_URL + Constants.PERSONAL_URL + "{"+id+"}";*/
                        String email = data.getString("email");
                        Toast.makeText(Signup.this, "Account created Successfully- " + "email:" + email +"id: "+id, Toast.LENGTH_SHORT).show();
                        /*openPersonalinfo();*/
                        Intent intent = new Intent(Signup.this, Personalinfo.class);
                        intent.putExtra("IDno",URl);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Signup.this,"Error - Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", "Error logging in");

                }
            });
            queue.add(request);
        }
    }
        public void layout () {
            userName = findViewById(R.id.userName);
            PassWord = findViewById(R.id.PassWord);
            Signup = findViewById(R.id.SignupBn);
            queue = Volley.newRequestQueue(this);
    }

    }
