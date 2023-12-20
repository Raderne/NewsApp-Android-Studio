package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.red.newsapp.Services.ApiInitialize;
import com.red.newsapp.Services.Articles.ArticlesSchema;
import com.red.newsapp.api_response.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditArticleActivity extends AppCompatActivity {
    String id, title, description, content, url, category;
    private EditText titleEditText, descriptionEditText, contentEditText, urlEditText;
    private Spinner categorySpinner;
    private Button updateArticleButton, cancelUpdateArticleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);

        SharedPreferences sharedPreferences = getSharedPreferences("com.red.newsapp", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        initViews();
        setValues();

        updateArticle(token);

    }

    private void updateArticle(String token) {
        updateArticleButton.setOnClickListener(view -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String content = contentEditText.getText().toString();
            String url = urlEditText.getText().toString();
            String category = categorySpinner.getSelectedItem().toString();
            String img = "";

            Retrofit retrofit = ApiInitialize.apiCall();
            API api = retrofit.create(API.class);
            ArticlesSchema articlesSchema = new ArticlesSchema(title, description, content, url, img, category);
            Call<ArticlesSchema> call = api.updateArticle(id, articlesSchema, "Bearer " + token);
            call.enqueue(new Callback<ArticlesSchema>() {
                @Override
                public void onResponse(Call<ArticlesSchema> call, Response<ArticlesSchema> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EditArticleActivity.this, "Makale başarıyla güncellendi", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditArticleActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(EditArticleActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArticlesSchema> call, Throwable t) {
                    Toast.makeText(EditArticleActivity.this, "Hata " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void setValues() {
        titleEditText.setText(title);
        descriptionEditText.setText(description);
        contentEditText.setText(content);
        urlEditText.setText(url);

        for (int i = 0; i < categorySpinner.getCount(); i++) {
            if (categorySpinner.getItemAtPosition(i).toString().equals(category)) {
                categorySpinner.setSelection(i);
                break;
            }
        }
    }

    private void initViews() {
        titleEditText = findViewById(R.id.idEtUpdateArticleTitle);
        descriptionEditText = findViewById(R.id.idEtUpdateArticleDescription);
        contentEditText = findViewById(R.id.idEtUpdateArticleContent);
        urlEditText = findViewById(R.id.idEtUpdateArticleUrl);
        categorySpinner = findViewById(R.id.idSpUpdateCategory);
        updateArticleButton = findViewById(R.id.idBtnUpdateArticle);
        cancelUpdateArticleButton = findViewById(R.id.idBtnUpdateArticleCancel);

        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        content = getIntent().getStringExtra("content");
        url = getIntent().getStringExtra("url");
        category = getIntent().getStringExtra("category");
    }
}