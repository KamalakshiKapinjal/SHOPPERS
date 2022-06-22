package com.example.travellers.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.travellers.R;

public class PaymentActivity extends AppCompatActivity {

    TextView total;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        total=findViewById(R.id.total_amt);

        toolbar=findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        double amount=0.0;
        amount=getIntent().getDoubleExtra("amount",0);

        total.setText("Rs"+amount);
    }
}