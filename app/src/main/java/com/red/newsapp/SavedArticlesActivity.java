package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.red.newsapp.api_response.Article;
import com.red.newsapp.Services.cloud_database.Firebase_storage;
import com.red.newsapp.news_adapters.SavedArticlesAdapter;

import java.util.ArrayList;

public class SavedArticlesActivity extends AppCompatActivity {
    private RecyclerView savedArticlesRV;
    private ScrollView scrollView;
    private ArrayList<Article> savedArticlesArrayList;
    public SavedArticlesAdapter savedArticlesAdapter;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_articles);
        initViews();

        savedArticlesRV.setAdapter(savedArticlesAdapter);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(ScrollView.GONE);

        getCloudSavedArticles();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.app_bar_home){
                Intent intent = new Intent(SavedArticlesActivity.this, NewsActivity.class);
                startActivity(intent);
                finish();
            } else if(item.getItemId() == R.id.app_bar_profile){
                Intent intent = new Intent(SavedArticlesActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
            return false;
        });
    }

    private void getCloudSavedArticles() {
        savedArticlesArrayList.clear();
        try {
            progressBar.setVisibility(ProgressBar.GONE);
            scrollView.setVisibility(ScrollView.VISIBLE);
            Firebase_storage firebase_storage = new Firebase_storage();
            firebase_storage.getAllSavedArticles(savedArticlesAdapter, savedArticlesArrayList);
        } catch (Exception e) {
            Toast.makeText(this, "kayıtlı makale yok", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        savedArticlesRV = findViewById(R.id.rvSavedArticles);
        progressBar = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.idSCSavedArticles);
        bottomNavigationView = findViewById(R.id.idSavedBottomNavigationBar);

        savedArticlesArrayList = new ArrayList<>();
        savedArticlesAdapter = new SavedArticlesAdapter(savedArticlesArrayList, this);
    }
}