package com.example.tsfdata.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tsfdata.Constants;
import com.example.tsfdata.R;

import org.json.JSONException;
import org.json.JSONObject;

public class getProfDetails extends AppCompatActivity {
    private TextView DESIGNATION, ORGANISATION, STARTDATE, ENDDATE;
    private ImageView Certificate;
    private String Desig;
    private String Org;
    private String Start;
    private String End;
    private Button editprof, deleteProf;
    private String Deleteid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getprof);
        DESIGNATION = findViewById(R.id.DESIGNATION);
        ORGANISATION = findViewById(R.id.ORGANISATION2);
        STARTDATE = findViewById(R.id.STARTDATE);
        ENDDATE = findViewById(R.id.ENDDATE);
        editprof = findViewById(R.id.editprof);
        Certificate = findViewById(R.id.ProfilepicDIsplay);
        String Id = getIntent().getExtras().getString("URLID2");
        String url = Constants.BASE_URL + Constants.PROFESSIONAL_URL + Id;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject data = jsonObject.getJSONObject("data");
                    Desig = data.getString("designation");
                    Org = data.getString("organisation");
                    Start = data.getString("start_date");
                    End = data.getString("end_date");
                    Deleteid=data.getString("id");
                    DESIGNATION.setText(Desig);
                    ORGANISATION.setText(Org);
                    STARTDATE.setText(Start);
                    ENDDATE.setText(End);
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
        editprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Int = new Intent(getProfDetails.this, com.example.tsfdata.editProfdetail.class);
                String Id = getIntent().getExtras().getString("URLID2");
                Int.putExtra("profid", Id);
                startActivity(Int);
            }
        });
        deleteProf=findViewById(R.id.deleteprof);
        deleteProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
    }

    private void delete() {

        RequestQueue queue;
        queue = Volley.newRequestQueue(this);
        String urlpro = Constants.BASE_URL + Constants.PROFESSIONAL_URL + Deleteid;
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.DELETE, urlpro, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String status = jsonObject.getString("status_message");
                    Toast.makeText(getProfDetails.this, status, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getProfDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest2);
    }
}
