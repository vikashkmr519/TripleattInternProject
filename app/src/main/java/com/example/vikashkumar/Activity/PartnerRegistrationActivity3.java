package com.example.vikashkumar.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.vikashkumar.R;

public class PartnerRegistrationActivity3 extends AppCompatActivity {

    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_registration3);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(PartnerRegistrationActivity3.this);
                LayoutInflater inflater = LayoutInflater.from(PartnerRegistrationActivity3.this);
                View mView = inflater.inflate(R.layout.alert_dialogue,null);
                Button download = mView.findViewById(R.id.btnDownload);
                Button cancel = mView.findViewById(R.id.btncancle);
                alert.setView(mView);




                final AlertDialog alertDialog = alert.create();
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                alert.show();
            }
        });
    }
}