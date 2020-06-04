package com.example.vikashkumar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikashkumar.Database.DatabaseClient;
import com.example.vikashkumar.Database.User;
import com.example.vikashkumar.R;
import com.example.vikashkumar.Utils.Preferences;

import org.w3c.dom.Text;

import java.util.List;

public class PartnerRegistrationActivity2 extends AppCompatActivity {

    Button btnSubmit;
    TextView name, adress,mobile,email,aadhar;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_registration2);


        preferences = new Preferences(getBaseContext());
        name = findViewById(R.id.name);
        adress = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        aadhar = findViewById(R.id.aadhar);


        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PartnerRegistrationActivity2.this,PartnerRegistrationActivity3.class);
                startActivity(i);
            }
        });

        getUser();
    }


    private void getUser() {


        class getUSer extends AsyncTask<Void, Void, List<User>> {


            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> user = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .UserDao()
                        .getUser(preferences.getToken());
                return user;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                if (users.size() != 0) {
                    aadhar.setText(users.get(0).getAadharId());
                    mobile.setText(users.get(0).getMobileNumber());
                    name.setText(users.get(0).getName());
                    email.setText(users.get(0).getEmailId());
                    adress.setText(users.get(0).getAddress());
                }else{
                    Toast.makeText(PartnerRegistrationActivity2.this, "There is no such User", Toast.LENGTH_SHORT).show();
                }
            }
        }
        getUSer gu = new getUSer();
        gu.execute();

    }
}