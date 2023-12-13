package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.red.newsapp.Services.cloud_database.Firebase_storage;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {
    String title, content, imageUrl, newsUrl, author, description;
    private ImageView newsDetailsImage;
    private TextView newsDetailsTitle, newsDetailsAuthor, newsDetailsContent, newsDetailsDescription, newsDetailsUrl;
    private ImageButton backBtn;
    private FloatingActionButton bookmarkBtn;
    private String documentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViews();

        Firebase_storage firebase_storage = new Firebase_storage();
        if (firebase_storage.db.collection("articles").document(title).get().isSuccessful()) {
            bookmarkBtn.setImageResource(R.drawable.bookmark_filled);
        }

        setViews();

        newsDetailsUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(newsUrl));
                startActivity(browserIntent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewsDetailsActivity.this, "Bu makale yer imlerine eklendi", Toast.LENGTH_SHORT).show();
            }
        });

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveArticle();
                bookmarkBtn.setImageResource(R.drawable.bookmark_filled);
            }
        });
    }

    private void saveArticle() {
        Firebase_storage firebase_storage = new Firebase_storage();
        try {
            // check if article exists in database before adding it to database to avoid duplicates (same title) in database
            if (firebase_storage.db.collection("articles").document(title).get().isSuccessful()) {
                Toast.makeText(this, "Bu makale zaten yer imlerinde", Toast.LENGTH_SHORT).show();
            } else {
                firebase_storage.createArticle(documentID ,title, content, imageUrl, newsUrl, author, description);
                Toast.makeText(NewsDetailsActivity.this, "Bu makale eklendi", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("NewsDetailsActivity", "saveArticle: " + e.getMessage());
        }
    }

    private void setViews() {
        newsDetailsTitle.setText(title);
        newsDetailsAuthor.setText(author);
        newsDetailsContent.setText(content);
        newsDetailsUrl.setText(newsUrl);
        newsDetailsDescription.setText(description);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(newsDetailsImage);
        } else {
            Picasso.get().load(R.drawable.newspaperbg).into(newsDetailsImage);
        }

        documentID = String.valueOf(System.currentTimeMillis());
    }

    private void initViews() {
        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        description = getIntent().getStringExtra("description");
        newsUrl = getIntent().getStringExtra("url");
        imageUrl = getIntent().getStringExtra("imageUrl");
        content = getIntent().getStringExtra("content");

        newsDetailsImage = findViewById(R.id.idDetailsNewsImage);
        newsDetailsTitle = findViewById(R.id.idDetailsNewsTitle);
        newsDetailsAuthor = findViewById(R.id.idDetailsNewsPublisher);
        newsDetailsContent = findViewById(R.id.idDetailsNewsContent);
        newsDetailsUrl = findViewById(R.id.idDetailsNewsUrl);
        newsDetailsDescription = findViewById(R.id.idDetailsNewsDescription);
        backBtn = findViewById(R.id.idBackButton);
        bookmarkBtn = findViewById(R.id.idDetailsNewsBookmark);
    }
}