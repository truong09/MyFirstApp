package com.learnadroid.myfirstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private TextView linkdangki;
    private Button dangnhap;
    private EditText username;
    private EditText password;

    ProgressDialog progressDialog;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        linkdangki = findViewById(R.id.signup_link);
        dangnhap = findViewById(R.id.bt_afp);
        connectionClass = new ConnectionClass();
        progressDialog = new ProgressDialog(this);
        username = findViewById(R.id.et_afp_email);
        password = findViewById(R.id.et_afp_password);

        //dangki
        linkdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, dangki.class));
            }
        });
        //dangnhap
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dologin dologin = new Dologin();
                dologin.execute();
            }
        });

    }

    private class Dologin extends AsyncTask<String, String, String> {

        String un = username.getText().toString();
        String pass = password.getText().toString();
        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            if (un.trim().equals("") || pass.trim().equals(""))
                z = "Please enter all fields....";
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Please check your internet connection";
                    } else {

                        String query = " select * from user where username='" + un + "'and password = '" + pass + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.first()) {
                            isSuccess = true;
                            z = "Login successfull - Mãi bên nhau bạn nhé!!";
                        } else {
                            isSuccess = false;
                            z = "Wrong password or username - Sai mật khẩu hoặc tên tài khoản rồi bạn mình ơi";
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions" + ex;
                }
            }
            return z;
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
            if (isSuccess) {
                Intent intent = new Intent(MainActivity.this, timkiem.class);
                startActivity(intent);
            }
            progressDialog.hide();
        }
    }

}


