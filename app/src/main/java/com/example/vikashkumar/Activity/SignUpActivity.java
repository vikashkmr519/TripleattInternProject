package com.example.vikashkumar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikashkumar.Database.DatabaseClient;
import com.example.vikashkumar.Database.User;
import com.example.vikashkumar.R;

public class SignUpActivity extends AppCompatActivity {
    TextView tvshopOnline, sellerRegistration;
    EditText aadharId,name,mobile,email,password,rePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tvshopOnline = findViewById(R.id.getYourshopOnline);
        sellerRegistration = findViewById(R.id.SellerRegistration);
        aadharId = findViewById(R.id.tietAadharId);
        name = findViewById(R.id.tietName);
        mobile = findViewById(R.id.tietMobile);
        email = findViewById(R.id.tietEmail);
        password = findViewById(R.id.tietPassword);
        rePassword = findViewById(R.id.tietReEnterPassword);



        tvshopOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, PartnerRegistration.class);
                startActivity(i);
            }
        });
        sellerRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(SignUpActivity.this, SellerRegistrationActivity.class);
//                startActivity(i);
                saveUser();
            }
        });
    }

    private void saveUser() {
        final String mAadhar = aadharId.getText().toString().trim();
        final String mName = name.getText().toString().trim();
        final String mMobile = mobile.getText().toString().trim();
        final String mEmail = email.getText().toString().trim();
        final String mPassword = password.getText().toString().trim();
        final String mRePassword = rePassword.getText().toString().trim();


        if (mName.isEmpty()) {
            name.setError("Name required");
            name.requestFocus();
            return;
        }

        if (mMobile.isEmpty()) {
            mobile.setError("Mobile Number required");
            mobile.requestFocus();
            return;
        }
        if (mEmail.isEmpty()) {
            email.setError("Email ID required");
            email.requestFocus();
            return;
        }

        if (mPassword.isEmpty()) {
            password.setError("Password required");
            password.requestFocus();
            return;
        }
        if (mRePassword.isEmpty()) {
            rePassword.setError("Re Enter Password required");
            rePassword.requestFocus();
            return;
        }

        if(!mRePassword.equals(mPassword)){
            rePassword.setError("Password Doesn't match");
            rePassword.requestFocus();
            password.setError("Password Doesn't match");
            password.requestFocus();
            return ;
        }

        class  saveUser extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.setAadharId(mAadhar);
                user.setEmailId(mEmail);
                user.setMobileNumber(mMobile);
                user.setName(mName);
                user.setPassword(mPassword);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .UserDao()
                        .insert(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


                Toast.makeText(getBaseContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();


            }


        }
        saveUser sv = new saveUser();
        sv.execute();
    }
}