package com.example.tsfdata;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Certificateupload extends AppCompatActivity {


    private ImageView Certificate;
    private final int IMAGE_REQUEST = 1;
    private RequestQueue queue;
    private Button Save;
    private static String url = "http://139.59.65.145:9090//user/educationdetail/certificate/";
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificateupload);
Layout();
    Certificate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectImage();
        }
    });
    Save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UploadIMG();
        }
    });
    }

    private void Layout(){
        Certificate = findViewById(R.id.imageView4);
        Save = findViewById(R.id.uploadCertificate);
        queue= Volley.newRequestQueue(this);
    }
private void selectImage(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Certificate.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
   private byte[] getFileDataFromDrawable(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
   }
private void UploadIMG(){
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(url, null, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try{
                    JSONObject obj = new JSONObject(new String(response.data));
                    if (obj.toString().contains("Success")){
                        Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_SHORT).show();
                    OpenProfessionaldetails();
                    }else{
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> params = new HashMap<>();
                String id = getIntent().getExtras().getString("IDNO");
                params.put("uid",id);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,DataPart> params = new HashMap<>();
                long imageName = System.currentTimeMillis();
                params.put("photo",new DataPart(imageName +".jpg",getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
queue.add(volleyMultipartRequest);
}
private void OpenProfessionaldetails(){
        Intent newintent = new Intent(Certificateupload.this,professionaldetail.class);
        String IDnumber = getIntent().getExtras().getString("IDNO");
        newintent.putExtra("IDnumber",IDnumber);
        startActivity(newintent);
}
}