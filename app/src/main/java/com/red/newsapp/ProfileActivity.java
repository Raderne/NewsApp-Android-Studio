package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private CardView cardView;
    private ScrollView scrollView;
    private Button loginButton, registerButton, logoutButton;
    private TextView nameTextView, lastNameTextView, emailTextView, personalInfoTextView, allArticlesTextView;

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
            scrollView.setVisibility(ScrollView.VISIBLE);
            SetUserValues();
        } else {
            cardView.setVisibility(CardView.VISIBLE);
            scrollView.setVisibility(ScrollView.GONE);

            loginButton.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            });

            registerButton.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

    private void SetUserValues() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.red.newsapp", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);
        String name = sharedPreferences.getString("name", null);
        String lastName = sharedPreferences.getString("lastName", null);

        nameTextView.setText(name);
        lastNameTextView.setText(lastName);
        emailTextView.setText(email);

//        personalInfoTextView.setOnClickListener(v -> {
//            Intent intent = new Intent(ProfileActivity.this, PersonalInfoActivity.class);
//            startActivity(intent);
//            finish();
//        });
//
//        allArticlesTextView.setOnClickListener(v -> {
//            Intent intent = new Intent(ProfileActivity.this, AllArticlesActivity.class);
//            startActivity(intent);
//            finish();
//        });

        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            scrollView.setVisibility(ScrollView.GONE);
            cardView.setVisibility(CardView.VISIBLE);

            loginButton.setOnClickListener(v1 -> {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            });

            registerButton.setOnClickListener(v1 -> {
                Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            });
        });
    }

    private void intiViews() {
        bottomNavigationView = findViewById(R.id.idProfileBottomNavigationBar);
        cardView = findViewById(R.id.idProfileLoginCardView);
        loginButton = findViewById(R.id.idBtnLogin);
        registerButton = findViewById(R.id.idBtnRegister);
        logoutButton = findViewById(R.id.idProfileBtnLogout);
        nameTextView = findViewById(R.id.idTvProfileName);
        lastNameTextView = findViewById(R.id.idTvProfileLastName);
        emailTextView = findViewById(R.id.idTvProfileEmail);
        personalInfoTextView = findViewById(R.id.idProfileBtnEdit);
        allArticlesTextView = findViewById(R.id.idProfileBtnAllArticles);
        scrollView = findViewById(R.id.idProfileScrollView);
    }
}