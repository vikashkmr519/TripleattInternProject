package com.example.vikashkumar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikashkumar.Database.DatabaseClient;
import com.example.vikashkumar.R;
import com.example.vikashkumar.Utils.Constant;
import com.example.vikashkumar.Utils.Preferences;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    TextView signUp,ForgotPassword;
    Preferences preferences;
    TextInputEditText enterid, enterPassword;
    CheckBox rememberMe;
    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        preferences = new Preferences(getBaseContext());
        btnlogin = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.signUp);
        ForgotPassword = findViewById(R.id.forgotPassword);
        enterid = findViewById(R.id.tietLogin);
        enterPassword = findViewById(R.id.tietPassword);
        rememberMe = findViewById(R.id.RememberMeCheckbox);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkingCredential();
            }
        });
    }


    private void checkingCredential() {
        final String username = enterid.getText().toString().trim();
        final String password = enterPassword.getText().toString().trim();

        if(username.isEmpty()){
            enterid.setError("It can Not be Empty");
            enterid.requestFocus();
            return;
        }

        if(password.isEmpty()){
            enterPassword.setError("It can Not be Empty");
            enterPassword.requestFocus();
            return;
        }

         class loginUser extends AsyncTask<Void,Void,Void>{

             String getPassword="";
             @Override
             protected Void doInBackground(Void... voids) {
                 String getPassword =DatabaseClient
                         .getInstance(getApplicationContext())
                         .getAppDatabase()
                         .UserDao()
                         .getPassword(username);
                 return null;
             }

             @Override
             protected void onPostExecute(Void aVoid) {
                 super.onPostExecute(aVoid);
                 if(!getPassword.equals(password)){
                     Toast.makeText(getBaseContext(),"You are Not Registered. Sign Up!!",Toast.LENGTH_SHORT).show();
                     return ;
                 }else{
                     Toast.makeText(getBaseContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
                     Random rand = new Random();
                     int token = 1000000000+rand.nextInt(900000000);
                     Preferences preferences = new Preferences(getBaseContext());
                     preferences.setToken(String.valueOf(token));
                     startActivity(new Intent(LoginActivity.this,SignUpActivity.class));

                 }
             }


         }
        loginUser lu = new loginUser();
        lu.execute();
    }
}