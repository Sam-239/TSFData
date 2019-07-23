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

public class editProfdetail extends AppCompatActivity {
    private EditText Designation,OrganiSation,Startdate,Enddate;
    private Button SaveButton;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profdetail);
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
        String Idno = getIntent().getExtras().getString("profid");
        String url = Constants.BASE_URL + Constants.PROFESSIONAL_URL + Idno;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, regParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    int id = data.getInt("id");
                    Toast.makeText(editProfdetail.this, "Details Saved, go back to check updated details", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(editProfdetail.this, "Error", Toast.LENGTH_SHORT).show();

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


    private void Act_layout(){
        Designation = findViewById(R.id.Designationedit);
        OrganiSation = findViewById(R.id.OrganiSationedit);
        Startdate = findViewById(R.id.Start_Dateedit);
        Enddate = findViewById(R.id.End_Dateedit);
        SaveButton = findViewById(R.id.saveProfessionaldetedit);
        queue = Volley.newRequestQueue(this);
    }



}

