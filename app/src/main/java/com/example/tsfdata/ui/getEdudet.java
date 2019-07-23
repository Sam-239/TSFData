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
import com.android.volley.toolbox.Volley;;
import com.example.tsfdata.Constants;
import com.example.tsfdata.R;

import org.json.JSONException;
import org.json.JSONObject;

public class getEdudet extends AppCompatActivity {
    private TextView DEGREE, ORGANISATION1, LOCATION1, STARTYR, ENDYR;
    private ImageView Certificate;
    private Button edit,deleteEdu;
    private String Deg;
    private String orgn;
    private String loc;
    private String styr;
    private String endyr;
private String Deleteid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getedu);
        DEGREE = findViewById(R.id.DEGREE);
        ORGANISATION1 = findViewById(R.id.ORGANISATION);
        STARTYR = findViewById(R.id.STARTYEAR);
        ENDYR = findViewById(R.id.ENDYEAR);
        LOCATION1 = findViewById(R.id.LOCATION2);
        Certificate = findViewById(R.id.Certidisplay);
        String Id = getIntent().getExtras().getString("URLID1");

        String url = Constants.BASE_URL + Constants.EDU_URL + Id;
        final String URL = Constants.BASE_URL + Constants.CERTIFICATE_URL + Id;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject data = jsonObject.getJSONObject("data");
                    Deg = data.getString("degree");
                    orgn = data.getString("organisation");
                    styr = data.getString("start_year");
                    endyr = data.getString("end_year");
                    loc = data.getString("location");
                    Deleteid=data.getString("id");
                    DEGREE.setText(Deg);
                    ORGANISATION1.setText(orgn);
                    STARTYR.setText(styr);
                    ENDYR.setText(endyr);
                    LOCATION1.setText(loc);
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
        ImageRequest imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Certificate.setImageBitmap(response);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(imageRequest);
        edit = findViewById(R.id.editEDU);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getEdudet.this, com.example.tsfdata.editedu.class);
                String Id = getIntent().getExtras().getString("URLID1");
                intent.putExtra("editid", Id);
                startActivity(intent);
                }
        });
deleteEdu=findViewById(R.id.deleteedu);
deleteEdu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        delete();
    }
    });
    }
private void delete(){

    RequestQueue queue;
    queue = Volley.newRequestQueue(this);
    String urlpro=Constants.BASE_URL+Constants.EDU_URL+Deleteid;
    JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.DELETE, urlpro, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                String status = jsonObject.getString("status_message");
                Toast.makeText(getEdudet.this,status,Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getEdudet.this,error.toString(),Toast.LENGTH_SHORT).show();
        }
    });
    queue.add(jsonObjectRequest2);
}
}


