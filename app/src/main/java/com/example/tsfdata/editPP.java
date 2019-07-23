package com.example.tsfdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class editPP extends AppCompatActivity {
    private RequestQueue queue;
    private ImageView Profile;
    private static final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    Uri ImgUri;
    private Button Upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pp);
        LayoutView();
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uploadimg();
            }
        });
    }
    private void Uploadimg(){
        Map<String,String> PARAMS = new HashMap<>();
        String uid = getIntent().getExtras().getString("ppid");
        String imgData = ImgtoString(bitmap);
        PARAMS.put("photo",imgData);
        PARAMS.put("uid",uid);
        String URL = Constants.BASE_URL+Constants.IMG_URL;
        JSONObject params = new JSONObject(PARAMS);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response;
                    String msg = data.getString("status_message");
                    Toast.makeText(editPP.this, "Successfully uploaded" ,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(editPP.this, "error" ,Toast.LENGTH_SHORT).show();

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
    private void SelectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK&& requestCode == IMG_REQUEST && data!= null) {
            ImgUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ImgUri);
                Profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String ImgtoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }

    private void LayoutView(){
        Profile = findViewById(R.id.profilePicedit);
        Upload = findViewById(R.id.uploadProfilepicedit);
        queue = Volley.newRequestQueue(this);
    }
}

