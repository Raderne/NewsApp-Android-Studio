package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private CardView cardView;
    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("com.red.newsapp", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

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

        if (token != null) {
            cardView.setVisibility(CardView.GONE);
        } else {
            cardView.setVisibility(CardView.VISIBLE);
            loginButton.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

    private void intiViews() {
        bottomNavigationView = findViewById(R.id.idProfileBottomNavigationBar);
        cardView = findViewById(R.id.idProfileLoginCardView);
        loginButton = findViewById(R.id.idBtnLogin);
        registerButton = findViewById(R.id.idBtnRegister);
    }
}