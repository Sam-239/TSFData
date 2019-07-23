package com.example.tsfdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Toggle extends AppCompatActivity {
private Button OpenPersonal,OpenEdu,OpenProf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);
        OpenPersonal=findViewById(R.id.OpenPersonal);
        OpenEdu = findViewById(R.id.openEducation);
        OpenProf=findViewById(R.id.openProf);
        OpenPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Toggle.this, com.example.tsfdata.ui.getPersonaldetails.class);
                String Id = getIntent().getExtras().getString("IDNUMBER1");
                intent1.putExtra("URLID", Id);
                startActivity(intent1);
            }


        });
OpenEdu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent2= new Intent(Toggle.this,com.example.tsfdata.ui.getEdudet.class);
        String Id1 = getIntent().getExtras().getString("IDNUMBER1");
        intent2.putExtra("URLID1", Id1);
        startActivity(intent2);
    }
});
OpenProf.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent3= new Intent(Toggle.this,com.example.tsfdata.ui.getProfDetails.class);
        String Id2 = getIntent().getExtras().getString("IDNUMBER1");
        intent3.putExtra("URLID2", Id2);
        startActivity(intent3);
    }
});
    }
}
