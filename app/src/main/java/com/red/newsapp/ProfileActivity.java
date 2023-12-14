package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        intiViews();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.app_bar_home){
                Intent intent = new Intent(ProfileActivity.this, NewsActivity.class);
                startActivity(intent);
                finish();
            } else if(item.getItemId() == R.id.app_bar_bookmark){
                Intent intent = new Intent(ProfileActivity.this, SavedArticlesActivity.class);
                startActivity(intent);
                finish();
            }
            return false;
        });
    }

    private void intiViews() {
        bottomNavigationView = findViewById(R.id.idProfileBottomNavigationBar);
    }
}