package com.example.travellers.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.travellers.R;
import com.example.travellers.models.NewProductModels;
import com.example.travellers.models.PopularProductsModel;
import com.example.travellers.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price,quantity;
    Button addToCart;
    ImageView addItems, removeItems;
    int totalQuantity=1;
    int totalPrice=0;

    Toolbar toolbar;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    //new products
    NewProductModels newProductModels =null;

    //popular products
    PopularProductsModel popularProductsModel=null;

    //show all products
    ShowAllModel showAllModel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar=findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductModels) {
            newProductModels = (NewProductModels) obj;
        }
        else if(obj instanceof PopularProductsModel){
            popularProductsModel=(PopularProductsModel) obj;
        }

        else if(obj instanceof ShowAllModel){
            showAllModel=(ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detailed_img);
        quantity=findViewById(R.id.quantity);
        rating = findViewById(R.id.rating);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);
        addToCart = findViewById(R.id.add_to_cart);

        //new products
            if(newProductModels!=null){
                Glide.with(getApplicationContext()).load(newProductModels.getImg_url()).into(detailedImg);
                name.setText(newProductModels.getName());
                rating.setText(newProductModels.getRating());
                description.setText(newProductModels.getDescription());
                price.setText(String.valueOf(newProductModels.getPrice()));
                name.setText(newProductModels.getName());

                totalPrice= newProductModels.getPrice()* totalQuantity;
            }

            //popular products
        if(popularProductsModel!=null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            name.setText(popularProductsModel.getName());

            totalPrice= popularProductsModel.getPrice()* totalQuantity;
        }

        //show all products
        if(showAllModel!=null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            name.setText(showAllModel.getName());

            totalPrice= showAllModel.getPrice()* totalQuantity;
        }

        //add to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        //add items
        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if(newProductModels!=null){
                        totalPrice= newProductModels.getPrice()* totalQuantity;
                    }

                    if(popularProductsModel!=null){
                        totalPrice=popularProductsModel.getPrice()* totalQuantity;
                    }

                    if(showAllModel!=null){
                        totalPrice= showAllModel.getPrice()* totalQuantity;
                    }
                }
            }
        });

        //remove items
        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });
    }

    private void addToCart() {
        String saveCurrentDate,saveCurrentTime;

        Calendar calForDate=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap =new HashMap<>();

        cartMap.put("productName",name.getText().toString());
        cartMap.put("productsPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                Toast.makeText(DetailedActivity.this,"Added to cart",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}