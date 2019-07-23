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

public class getPersonaldetails extends AppCompatActivity {
    private TextView NAME,MOBILE,LOCATION1,LINKS,SKILLS;
    private ImageView ProfiePICedit;
    private Button editpersonal,deletepersonal;
    private RequestQueue queue;
    private String Name;
    private String Mobile;
    private String Location;
    private String Links;
    private String Skills;
    private String Deleteid;
    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getpersonal);
        NAME = findViewById(R.id.NAME);
        MOBILE = findViewById(R.id.MOBILE);
        LOCATION1 = findViewById(R.id.LOCATION);
        LINKS = findViewById(R.id.LINKS);
        SKILLS = findViewById(R.id.SKILLS);
        queue = Volley.newRequestQueue(this);
        ProfiePICedit = findViewById(R.id.ProfilepicDIsplay);
        editpersonal=findViewById(R.id.editPersonal);
        String Id = getIntent().getExtras().getString("URLID");
        String url = Constants.BASE_URL + Constants.PERSONAL_URL + Id;
        String URL = Constants.BASE_URL + Constants.PROFILEPIC_URL + Id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject data = jsonObject.getJSONObject("data");
                    Skills = data.getString("skills");
                    Name = data.getString("name");
                    Mobile = data.getString("mobile_no");
                    Location = data.getString("location");
                    Links = data.getString("links");
                    Deleteid=data.getString("id");
                    NAME.setText(Name);
                    MOBILE.setText(Mobile);
                    LOCATION1.setText(Location);
                    LINKS.setText(Links);
                    SKILLS.setText(Skills);
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
editpersonal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getPersonaldetails.this,com.example.tsfdata.editPersonalinfo.class);
        String Id = getIntent().getExtras().getString("URLID");
        intent.putExtra("personalid",Id);
    startActivity(intent);
    }
});
        ImageRequest imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ProfiePICedit.setImageBitmap(response);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
ProfiePICedit.setImageBitmap(null);
            }
        });
        queue.add(imageRequest);
    ProfiePICedit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         Intent intent = new Intent(getPersonaldetails.this,com.example.tsfdata.editPP.class);
            String Id = getIntent().getExtras().getString("URLID");
            intent.putExtra("ppid",Id);
         startActivity(intent);
        }
    });
}
}

