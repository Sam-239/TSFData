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

public class education extends AppCompatActivity {
private EditText Degree,Organisation,Start,End, LOC;
private Button SAVE;
   private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
LAYOUT_VIEW();
    SAVE.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SaveDetails();
        }
    });

    }
    private void SaveDetails() {
        String degree = Degree.getText().toString();
        String Org = Organisation.getText().toString();
        String startYr = Start.getText().toString();
        String endYr = End.getText().toString();
        String loc = LOC.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("start_year", startYr);
        params.put("degree", degree);
        params.put("organisation", Org);
        params.put("location", loc);
        params.put("end_year",endYr);
        JSONObject regParams = new JSONObject(params);
        String Id = getIntent().getExtras().getString("uID");
        String url = Constants.BASE_URL + Constants.EDU_URL+Id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, regParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    int id = data.getInt("id");
                    Toast.makeText(education.this, "Details Saved Successfully", Toast.LENGTH_SHORT).show();
                    Intent INTENT = new Intent(education.this,Certificateupload.class);
                    String IDNO = getIntent().getExtras().getString("uID");
                    INTENT.putExtra("IDNO",IDNO);
                    startActivity(INTENT);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(education.this, "Error", Toast.LENGTH_SHORT).show();

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
    private void LAYOUT_VIEW() {
Degree = findViewById(R.id.Degree);
Organisation = findViewById(R.id.organiSationtext);
Start = findViewById(R.id.start__Year);
LOC = findViewById(R.id.LoCation);
End = findViewById(R.id.end__Year);
SAVE = findViewById(R.id.saveEduDet);
queue = Volley.newRequestQueue(this);
    }
}