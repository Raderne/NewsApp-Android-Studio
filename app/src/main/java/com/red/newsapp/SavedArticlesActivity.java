package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.red.newsapp.api_response.GONDER;
import com.red.newsapp.news_adapters.SavedArticlesAdapter;

import java.util.ArrayList;

public class SavedArticlesActivity extends AppCompatActivity {
    private RecyclerView savedArticlesRV;
    private ScrollView scrollView;
    private ArrayList<GONDER> savedArticlesArrayList;
    private SavedArticlesAdapter savedArticlesAdapter;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_articles);
        initViews();


        savedArticlesRV.setAdapter(savedArticlesAdapter);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        scrollView.setVisibility(ScrollView.GONE);

        getCloudSavedArticles();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.app_bar_home){
                Intent intent = new Intent(SavedArticlesActivity.this, NewsActivity.class);
                startActivity(intent);
            }
            return true;
        });
    }

    private void getCloudSavedArticles() {
        savedArticlesArrayList.clear();
        try {
            progressBar.setVisibility(ProgressBar.GONE);
            scrollView.setVisibility(ScrollView.VISIBLE);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("articles").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().size(); i++) {
                        GONDER article = new GONDER();
                        article.setTitle(task.getResult().getDocuments().get(i).get("title").toString());
                        article.setDescription(task.getResult().getDocuments().get(i).get("description").toString());
                        article.setUrlToImage(task.getResult().getDocuments().get(i).get("imageUrl").toString());
                        article.setUrl(task.getResult().getDocuments().get(i).get("newsUrl").toString());
                        article.setAuthor(task.getResult().getDocuments().get(i).get("author").toString());
                        article.setContent(task.getResult().getDocuments().get(i).get("content").toString());
                        savedArticlesArrayList.add(article);
                    }
                    savedArticlesAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "kayıtlı makale yok", Toast.LENGTH_SHORT).show();
                }
            });
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