package com.learnadroid.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    Button btTieptuc2;
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btTieptuc2 = (Button) findViewById(R.id.btTieptuc2);
        bt1 = (Button) findViewById(R.id.bt1);
        btTieptuc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mh3 = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(mh3);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Email has been sent",Toast.LENGTH_LONG).show();
            }
        });
    }
}