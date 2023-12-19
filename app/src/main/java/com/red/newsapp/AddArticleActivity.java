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

public class AddArticleActivity extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText, urlEditText, contentEditText;
    private Spinner categorySpinner;
    private Button addArticleButton, cancelAddArticleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        SharedPreferences sharedPreferences = getSharedPreferences("com.red.newsapp", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        intiViews();

        addArticle(token);

        cancelAddArticleButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddArticleActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void addArticle(String token) {
        addArticleButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String url = urlEditText.getText().toString();
            String content = contentEditText.getText().toString();
            String category = categorySpinner.getSelectedItem().toString();
            String img = "";

            if (title.isEmpty() || description.isEmpty() || url.isEmpty() || content.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                Retrofit retrofit = ApiInitialize.apiCall();
                API api = retrofit.create(API.class);
                ArticlesSchema articlesSchema = new ArticlesSchema(title, description, content, url, img, category);
                Call<ArticlesSchema> call = api.createArticle(articlesSchema, "Bearer " + token);
                call.enqueue(new Callback<ArticlesSchema>() {
                    @Override
                    public void onResponse(Call<ArticlesSchema> call, Response<ArticlesSchema> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddArticleActivity.this, "Makale başarıyla eklendi", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddArticleActivity.this, ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddArticleActivity.this, "Bir şeyler yanlış gitti.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticlesSchema> call, Throwable t) {
                        Toast.makeText(AddArticleActivity.this, "Hata " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void intiViews() {
        titleEditText = findViewById(R.id.idEtArticleTitle);
        descriptionEditText = findViewById(R.id.idEtArticleDescription);
        urlEditText = findViewById(R.id.idEtArticleUrl);
        contentEditText = findViewById(R.id.idEtArticleContent);
        categorySpinner = findViewById(R.id.idSpCategory);
        addArticleButton = findViewById(R.id.idBtnAddArticle);
        cancelAddArticleButton = findViewById(R.id.idBtnAddArticleCancel);
    }
}