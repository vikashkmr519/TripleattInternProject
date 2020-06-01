package com.example.vikashkumar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vikashkumar.R;

public class PartnerRegistrationActivity2 extends AppCompatActivity {

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_registration2);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PartnerRegistrationActivity2.this,PartnerRegistrationActivity3.class);
                startActivity(i);
            }
        });
    }
}