package com.learnadroid.myfirstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class dangki extends AppCompatActivity {

    public static final String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]{2,}";
    public static final String passPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%,.]).{6,20})";
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;
    private Button btTieptuc;
    private EditText txtMail;
    private EditText txtPass;
    private TextView txtValidate;
    private TextView txtKT;
    private EditText txtHoten;
    private EditText txtTen;
    private EditText txtPhone;
    //Validation
    private Boolean isValidMail = false;
    private Boolean isValidpPass = false;
    private Boolean isPhone = false;
    private Boolean isPass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtKT = findViewById(R.id.txtKT);
        txtMail = findViewById(R.id.txtMail);
        txtPass = findViewById(R.id.txtPass);
        txtValidate = findViewById(R.id.txtValidate);
        btTieptuc = findViewById(R.id.btTieptuc);
        txtHoten = findViewById(R.id.txtHoten);
        txtTen = findViewById(R.id.txtTen);
        txtPhone = findViewById(R.id.txtPhone);


        connectionClass = new ConnectionClass();

        progressDialog = new ProgressDialog(this);

        btTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doregister doregister = new Doregister();
                doregister.execute("");
            }
        });
        txtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtKT.setText("");
                String pass = txtPass.getText().toString().trim();
                isValidpPass = (pass.matches(passPattern) && s.length() > 0);
                if (!isValidpPass) {
                    txtKT.setTextColor(Color.rgb(255, 0, 0));
                    txtKT.setText("0-9 && a-z && A-Z && @#$%.,");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //validation
        txtMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtValidate.setText("");
                String email = txtMail.getText().toString().trim();
                isValidMail = (email.matches(emailPattern) && s.length() > 0);
                if (!isValidMail) {
                    txtValidate.setTextColor(Color.rgb(255, 0, 0));
                    txtValidate.setText("Email không hợp lệ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public class Doregister extends AsyncTask<String, String, String> {

        String name = txtHoten.getText().toString();
        String email = txtMail.getText().toString();
        String phone = txtPhone.getText().toString();
        String username = txtTen.getText().toString();
        String password = txtPass.getText().toString();

        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            int id = 0;
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Please check your internet connection";
                } else {

                    //lay id
                    String queryId = "SELECT * FROM customer ORDER BY id_customer DESC LIMIT 1";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(queryId);
                    if (rs.first()) {
                        while (rs.next()) {
                            id = Integer.parseInt(rs.getString(1));
                            id += 1;
                        }
                    }
                    String newId = Integer.toString(id);

                    String query1 = "insert into customer values('" + newId + "','" + name + "','" + email + "','" + phone + "')";
                    String query2 = "insert into user values('" + newId + "','" + username + "','" + password + "','" + newId + "')";

                    try {
                        Statement stmt1 = con.createStatement();
                        stmt1.executeUpdate(query1);
                    } catch (Exception e) {
                        z = z + ";Exceptions1" + e;
                    }

                    try {
                        Statement stmt2 = con.createStatement();
                        stmt2.executeUpdate(query2);
                    } catch (Exception e) {
                        z = z + ";Exceptions2" + e;
                    }

                    z = "Email has been sent";
                    isSuccess = true;

                }
            } catch (Exception ex) {
                isSuccess = false;
                z = "Exceptions" + ex;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();

            if (isSuccess) {
                startActivity(new Intent(dangki.this, Main2Activity.class));
            }
            progressDialog.hide();
        }
    }

}
