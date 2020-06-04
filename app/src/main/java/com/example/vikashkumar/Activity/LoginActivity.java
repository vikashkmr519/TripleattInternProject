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

         class loginUser extends AsyncTask<Void,Void,String>{


             @Override
             protected String doInBackground(Void... voids) {
                 String getPassword =DatabaseClient
                         .getInstance(getApplicationContext())
                         .getAppDatabase()
                         .UserDao()
                         .getPassword(username);
                 return getPassword;
             }

             @Override
             protected void onPostExecute(String s) {
                 super.onPostExecute(s);
                 if(!s.equals(password)){
                     Toast.makeText(getBaseContext(),"You are Not Registered. Sign Up!!",Toast.LENGTH_SHORT).show();
                     return ;
                 }else{
                     Toast.makeText(getBaseContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
                     Random rand = new Random();
                     Preferences preferences = new Preferences(getBaseContext());




                     preferences.setUsername(enterid.getText().toString().trim());
                     Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                     i.putExtra("login",true);
                     startActivity(i);

                 }
             }
         }
        loginUser lu = new loginUser();
        lu.execute();
    }
}