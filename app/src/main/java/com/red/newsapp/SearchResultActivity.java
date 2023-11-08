package com.red.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.red.newsapp.api_response.API;
import com.red.newsapp.api_response.Articles;
import com.red.newsapp.api_response.Article;
import com.red.newsapp.news_adapters.EverythingNewsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchResultActivity extends AppCompatActivity {

    String searchedInput;

    private TextView tvSearchResult;
    private EditText etSearch;
    private ImageButton searchButton;
    private RecyclerView searchResultRV;
    private ArrayList<Article> searchResultArrayList;
    private EverythingNewsAdapter searchResultAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        searchedInput = getIntent().getStringExtra("searchInput");

        initViews();

        searchResultRV.setAdapter(searchResultAdapter);
        searchResultRV.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        getSearchedArticles(searchedInput);

        searchButton.setOnClickListener(v -> {
            String searchedInput = etSearch.getText().toString();
            if (searchedInput.isEmpty()) {
                Toast.makeText(this, "Lütfen arama yapmak istediğiniz kelimeyi giriniz.", Toast.LENGTH_SHORT).show();
            } else {
                getSearchedArticles(searchedInput);
            }
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            String searchedInput = etSearch.getText().toString();
            if (searchedInput.isEmpty()) {
                Toast.makeText(this, "Lütfen arama yapmak istediğiniz kelimeyi giriniz.", Toast.LENGTH_SHORT).show();
            } else {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getSearchedArticles(searchedInput);
                }
            }
            return false;
        });
    }

    private void getSearchedArticles(String searchedInput) {
        searchResultArrayList.clear();

        Retrofit retrofit = RetrofitInitialize.BaslangicAdim();
        API api = retrofit.create(API.class);
        Call<Articles> call = api.searchArticles(searchedInput, 30, "popularity", getString(R.string.api_key));
        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                Articles articles = response.body();
                if (articles != null) {
                    tvSearchResult.setText(searchedInput + " için yaklaşık " + articles.getTotalResults() + " sonuç bulundu.");
                    etSearch.setText(searchedInput);

                    ArrayList<Article> articlesArrayList = articles.getArticles();
                    for (int i = 0; i < articlesArrayList.size(); i++) {
                        searchResultArrayList.add(new Article(
                                articlesArrayList.get(i).getAuthor(),
                                articlesArrayList.get(i).getTitle(),
                                articlesArrayList.get(i).getDescription(),
                                articlesArrayList.get(i).getUrl(),
                                articlesArrayList.get(i).getUrlToImage(),
                                articlesArrayList.get(i).getContent()
                        ));
                    }
                    searchResultAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SearchResultActivity.this, "Haber Makaleleri Yok", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {
                Toast.makeText(SearchResultActivity.this, "Haber Makaleleri Yok", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        tvSearchResult = findViewById(R.id.tvSearchResultActivity);
        etSearch = findViewById(R.id.etSearchResultActivity);
        searchButton = findViewById(R.id.ibClearSearchResultActivity);
        searchResultRV = findViewById(R.id.rvSearchResultActivity);

        searchResultArrayList = new ArrayList<>();
        searchResultAdapter = new EverythingNewsAdapter(searchResultArrayList, this);
    }
}