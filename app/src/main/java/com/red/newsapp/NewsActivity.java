package com.red.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.red.newsapp.api_response.API;
import com.red.newsapp.api_response.Articles;
import com.red.newsapp.api_response.Article;
import com.red.newsapp.categories.CategoryRVAdapter;
import com.red.newsapp.categories.CategoryRVModal;
import com.red.newsapp.news_adapters.EverythingNewsAdapter;
import com.red.newsapp.news_adapters.TopHeadlinesNewsRVAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {
    private RecyclerView categoryRV, topHeadlinesNewsRV, bottomNewsRV;
    private ArrayList<Article> articlesArrayList;
    private ArrayList<Article> topHeadlinesArticlesArrayList;
    private ArrayList<CategoryRVModal> categoryModelArrayList;
    private TopHeadlinesNewsRVAdapter topHeadlinesNewsRVAdapter;
    private CategoryRVAdapter categoryRVAdapter;
    private EverythingNewsAdapter everythingNewsAdapter;

    private EditText etSearch;
    private ImageButton searchButton;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initViews();

        topHeadlinesNewsRV.setAdapter(topHeadlinesNewsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        bottomNewsRV.setAdapter(everythingNewsAdapter);

        getTopHeadlinesNews();
        getCategories();
        getBottomNews("General");

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchInput = etSearch.getText().toString();
                if (!searchInput.isEmpty()) {
                    Intent intent = new Intent(NewsActivity.this, SearchResultActivity.class);
                    intent.putExtra("searchInput", searchInput);
                    startActivity(intent);
                } else {
                    Toast.makeText(NewsActivity.this, "Lütfen bir arama terimi giriniz", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String searchedInput = etSearch.getText().toString();
                if (!searchedInput.isEmpty()) {
                    if(i == EditorInfo.IME_ACTION_DONE){
                        Intent intent = new Intent(NewsActivity.this, SearchResultActivity.class);
                        intent.putExtra("searchInput", searchedInput);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(NewsActivity.this, "Lütfen bir arama terimi giriniz", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.app_bar_bookmark) {
                    Intent intent = new Intent(NewsActivity.this, SavedArticlesActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }

    private void getBottomNews(String category) {
        articlesArrayList.clear();

        Retrofit retrofit = RetrofitInitialize.BaslangicAdim();
        API api = retrofit.create(API.class);
        Call<Articles> call = api.getNewsArticles("us", category, 20, getString(R.string.api_key));
        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                if (response.isSuccessful()) {
                    Articles articles = response.body();
                    ArrayList<Article> articlesArray = articles.getArticles();

                    for (int i = 0; i < articlesArray.size(); i++) {
                        articlesArrayList.add(new Article(
                                articlesArray.get(i).getAuthor(),
                                articlesArray.get(i).getTitle(),
                                articlesArray.get(i).getDescription(),
                                articlesArray.get(i).getUrl(),
                                articlesArray.get(i).getUrlToImage(),
                                articlesArray.get(i).getContent()
                        ));
                    }
                    everythingNewsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(NewsActivity.this, "Haber Makaleleri alınamadı", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {
                Toast.makeText(NewsActivity.this, "Haber Makaleleri alınamadı", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTopHeadlinesNews() {
        topHeadlinesArticlesArrayList.clear();

        Retrofit retrofit = RetrofitInitialize.BaslangicAdim();
        API api = retrofit.create(API.class);
        Call<Articles> call = api.getNewsArticles("us", "technology", 5, getString(R.string.api_key));
        call.enqueue(new retrofit2.Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, retrofit2.Response<Articles> response) {
                if (response.isSuccessful()) {
                    Articles articles = response.body();
                    ArrayList<Article> articlesArray = articles.getArticles();

                    for (int i = 0; i < articlesArray.size(); i++) {
                        topHeadlinesArticlesArrayList.add(new Article(
                                articlesArray.get(i).getAuthor(),
                                articlesArray.get(i).getTitle(),
                                articlesArray.get(i).getDescription(),
                                articlesArray.get(i).getUrl(),
                                articlesArray.get(i).getUrlToImage(),
                                articlesArray.get(i).getContent()
                        ));
                    }
                    topHeadlinesNewsRVAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(NewsActivity.this, "Haber Makaleleri alınamadı", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {
                Toast.makeText(NewsActivity.this, "Haber Makaleleri alınamadı", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCategories() {
        categoryModelArrayList.add(new CategoryRVModal("General", true));
        categoryModelArrayList.add(new CategoryRVModal("Business", false));
        categoryModelArrayList.add(new CategoryRVModal("Entertainment", false));
        categoryModelArrayList.add(new CategoryRVModal("Health", false));
        categoryModelArrayList.add(new CategoryRVModal("Science", false));
        categoryModelArrayList.add(new CategoryRVModal("Sports", false));
        categoryModelArrayList.add(new CategoryRVModal("Technology", false));
    }

    private void initViews() {
        etSearch = findViewById(R.id.etSearch);
        searchButton = findViewById(R.id.btnSearch);
        bottomNavigationView = findViewById(R.id.idBottomNavigationBar);

        categoryRV = findViewById(R.id.rvCategory);
        topHeadlinesNewsRV = findViewById(R.id.rvTopNews);
        bottomNewsRV = findViewById(R.id.rvEverythingNews);

        articlesArrayList = new ArrayList<>();
        topHeadlinesArticlesArrayList = new ArrayList<>();
        categoryModelArrayList = new ArrayList<>();
        topHeadlinesNewsRVAdapter = new TopHeadlinesNewsRVAdapter(topHeadlinesArticlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryModelArrayList, this, this::onCategoryClick);
        everythingNewsAdapter = new EverythingNewsAdapter(articlesArrayList, this);
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryModelArrayList.get(position).getCategory();
        getBottomNews(category);
    }
}