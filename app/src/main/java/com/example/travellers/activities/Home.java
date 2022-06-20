package com.example.travellers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.travellers.R;
import com.example.travellers.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    Fragment homeFragments;
    FirebaseAuth auth;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

            toolbar = findViewById(R.id.home_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        homeFragments=new HomeFragment();
        loadFragmnet(homeFragments);

        auth=FirebaseAuth.getInstance();
    }


    private void loadFragmnet(Fragment homeFragments) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragments);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

//    private void getMenuInflater(int main_menu, Menu menu) {
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();

        if(id==R.id.menu_logout){
            auth.signOut();
            startActivity(new Intent(Home.this,login_page.class));
            finish();
        }
        else if(id==R.id.my_cart){
            startActivity(new Intent(Home.this, CartActivity.class));

        }
        return true;
    }
}