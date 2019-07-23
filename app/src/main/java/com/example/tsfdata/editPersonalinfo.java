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

public class editPersonalinfo extends AppCompatActivity {
    private EditText fullName, emailId, Mobile, location, links, skills;
    private Button saveBn;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personalinfo);
            Layout();
            saveBn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveDetails();
                }
            });



        }

        private void saveDetails(){
            String Name = fullName.getText().toString();
            String email = emailId.getText().toString();
            String mobile = Mobile.getText().toString();
            String Skills = skills.getText().toString();
            String Links = links.getText().toString();
            String Location = location.getText().toString();
            Map<String, String> params = new HashMap<>();
            params.put("skills",Skills);
            params.put("mobile_no",mobile);
            params.put("name",Name);
            params.put("links",Links);
            params.put("location",Location);
            params.put("email",email);
            JSONObject regParams = new JSONObject(params);
            String Id = getIntent().getExtras().getString("personalid");
            String url = Constants.BASE_URL + Constants.PERSONAL_URL +Id;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, regParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        Toast.makeText(editPersonalinfo.this, "Details Saved Successfully,go back to check updated details",Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(editPersonalinfo.this,"Error",Toast.LENGTH_SHORT).show();

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
        private void Layout() {
            fullName = findViewById(R.id.Nameedit);
            emailId = findViewById(R.id.emailIDedit);
            Mobile = findViewById(R.id.mobileedit);
            location = findViewById(R.id.location1edit);
            links = findViewById(R.id.linksedit);
            queue = Volley.newRequestQueue(this);
            saveBn = findViewById(R.id.savePersonalDetedit);
            skills = findViewById(R.id.Skillsedit);
        }
    }

