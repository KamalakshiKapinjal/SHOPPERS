package com.example.travellers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.travellers.R;
import com.example.travellers.models.NewProductModels;
import com.example.travellers.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText name,address,city,postalCode,phoneNumber;
    Button addAddressBtn,moveToPayment;
    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar = findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //get data from detailed activity
        Object obj=getIntent().getSerializableExtra("item");

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        name=findViewById(R.id.ad_name);
        address=findViewById(R.id.ad_address);
        city=findViewById(R.id.ad_city);
        postalCode=findViewById(R.id.ad_code);
        phoneNumber=findViewById(R.id.ad_phone);
        addAddressBtn=findViewById(R.id.ad_add_address);
        moveToPayment=findViewById(R.id.ad_payment);

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userCity = city.getText().toString();
                String userAddress = address.getText().toString();
                String userCode = postalCode.getText().toString();
                String userPhone = phoneNumber.getText().toString();

                String final_address = "";

                if (!userName.isEmpty()) {
                    final_address += userName;
                }

                if (!userCity.isEmpty()) {
                    final_address += userCity;
                }

                if (!userAddress.isEmpty()) {
                    final_address += userAddress;
                }

                if (!userCode.isEmpty()) {
                    final_address += userCode;
                }

                if (!userPhone.isEmpty()) {
                    final_address += userPhone;
                }
                if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userPhone.isEmpty()) {

                    Map<String, String> map = new HashMap<>();
                    map.put("userAddress", final_address);

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())

                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(AddAddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(AddAddressActivity.this, "Kindly fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        moveToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double amount=0.0;
                if(obj instanceof NewProductModels){
                    NewProductModels newProductModels=(NewProductModels) obj;
                    amount= newProductModels.getPrice();
                }

                if(obj instanceof PopularProductsModel){
                    PopularProductsModel popularProductsModel=(PopularProductsModel) obj;
                    amount= popularProductsModel.getPrice();
                }

                Intent intent=new Intent(AddAddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);
            }
        });

        }
    }
