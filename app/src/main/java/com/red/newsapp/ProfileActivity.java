package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.red.newsapp.Services.ApiInitialize;
import com.red.newsapp.Services.Articles.ArticlesSchema;
import com.red.newsapp.api_response.API;
import com.red.newsapp.news_adapters.ProfileArticlesAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private CardView cardView;
    private ScrollView scrollView;
    private RecyclerView recyclerViewAllArticles;
    private ArrayList<ArticlesSchema> articlesArrayList;
    public ProfileArticlesAdapter profileArticlesAdapter;
    private Button loginButton, registerButton, logoutButton;
    private TextView nameTextView, lastNameTextView, emailTextView, addArticleTextView;

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
            getArticles(token);
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

    private void getArticles(String token) {
        articlesArrayList.clear();

        Retrofit retrofit = ApiInitialize.apiCall();
        API api = retrofit.create(API.class);
        Call<ArrayList<ArticlesSchema>> call = api.getUserArticles("Bearer " + token);
        call.enqueue(new retrofit2.Callback<ArrayList<ArticlesSchema>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ArrayList<ArticlesSchema>> call, retrofit2.Response<ArrayList<ArticlesSchema>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ArticlesSchema> articles = response.body();
                    if (articles != null) {
                        for (int i = 0; i < articles.size(); i++) {
                            articlesArrayList.add(new ArticlesSchema(
                                    articles.get(i).get_id(),
                                    articles.get(i).getAuthor(),
                                    articles.get(i).getTitle(),
                                    articles.get(i).getDescription(),
                                    articles.get(i).getContent(),
                                    articles.get(i).getUrl(),
                                    articles.get(i).getImg(),
                                    articles.get(i).getCategory()
                            ));
                        }
                        profileArticlesAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ArticlesSchema>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SetUserValues() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.red.newsapp", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);
        String name = sharedPreferences.getString("name", null);
        String lastName = sharedPreferences.getString("lastName", null);

        nameTextView.setText(name);
        lastNameTextView.setText(lastName);
        emailTextView.setText(email);


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
        addArticleTextView = findViewById(R.id.idProfileBtnAllArticles);
        scrollView = findViewById(R.id.idProfileScrollView);
        recyclerViewAllArticles = findViewById(R.id.idProfileRecyclerAllArticles);

        articlesArrayList = new ArrayList<>();
        profileArticlesAdapter = new ProfileArticlesAdapter(articlesArrayList, this);
        recyclerViewAllArticles.setAdapter(profileArticlesAdapter);

    }
}