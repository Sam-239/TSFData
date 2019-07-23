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

public class professionaldetail extends AppCompatActivity {
private EditText Designation,OrganiSation,Startdate,Enddate;
private Button SaveButton;
private RequestQueue queue;
@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_professionaldetail);
    Act_layout();
    SaveButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        SaveDet();
        }
        });
        }
private void SaveDet() {
        String desig= Designation.getText().toString();
        String organiSation = OrganiSation.getText().toString();
        String startDate = Startdate.getText().toString();
        String endDate = Enddate.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("end_date", endDate);
        params.put("organisation", organiSation);
        params.put("designation", desig);
        params.put("start_date", startDate);
        JSONObject regParams = new JSONObject(params);
        String Idno = getIntent().getExtras().getString("IDnumber");
        String url = Constants.BASE_URL + Constants.PROFESSIONAL_URL + Idno;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, regParams, new Response.Listener<JSONObject>() {
@Override
public void onResponse(JSONObject response) {
        try {
        JSONObject data = response.getJSONObject("data");
        int id = data.getInt("id");
        Toast.makeText(professionaldetail.this, "Details Saved, Login using email id", Toast.LENGTH_SHORT).show();
        Login();
        } catch (JSONException e) {
        e.printStackTrace();
        Toast.makeText(professionaldetail.this, "Error", Toast.LENGTH_SHORT).show();

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
private void Login(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

        }


private void Act_layout(){
        Designation = findViewById(R.id.Designation);
        OrganiSation = findViewById(R.id.OrganiSation);
        Startdate = findViewById(R.id.Start_Date);
        Enddate = findViewById(R.id.End_Date);
        SaveButton = findViewById(R.id.saveProfessionaldet);
        queue = Volley.newRequestQueue(this);
        }



        }
