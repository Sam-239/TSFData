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

public class Personalinfo extends AppCompatActivity {
    private EditText fullName, emailId, Mobile, location, links, skills;
    private Button saveBn;
    private RequestQueue queue;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_personalinfo);
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
        String Id = getIntent().getExtras().getString("IDno");
        String url = Constants.BASE_URL + Constants.PERSONAL_URL +Id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, regParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    Toast.makeText(Personalinfo.this, "Details Saved Successfully",Toast.LENGTH_SHORT).show();
                    openimgUpload();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Personalinfo.this,"Error",Toast.LENGTH_SHORT).show();

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
   private void openimgUpload(){
       Intent intent = new Intent(Personalinfo.this,Imageupload.class);
       String ID = getIntent().getExtras().getString("IDno");
       intent.putExtra("ID",ID);
       startActivity(intent);
   }
   private void Layout() {
        fullName = findViewById(R.id.Name);
        emailId = findViewById(R.id.emailID);
        Mobile = findViewById(R.id.mobile);
        location = findViewById(R.id.location1);
        links = findViewById(R.id.linkS);
        queue = Volley.newRequestQueue(this);
        saveBn = findViewById(R.id.savePersonalDet);
        skills = findViewById(R.id.Skills);
   }
}