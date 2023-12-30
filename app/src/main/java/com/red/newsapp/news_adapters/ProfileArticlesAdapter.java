package com.red.newsapp.news_adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.red.newsapp.EditArticleActivity;
import com.red.newsapp.NewsDetailsActivity;
import com.red.newsapp.R;
import com.red.newsapp.Services.ApiInitialize;
import com.red.newsapp.Services.Articles.ArticlesSchema;
import com.red.newsapp.api_response.API;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ProfileArticlesAdapter extends RecyclerView.Adapter<ProfileArticlesAdapter.ProfileArticlesViewHolder> {
    private ArrayList<ArticlesSchema> articlesArrayList;
    private Context context;

    public ProfileArticlesAdapter(ArrayList<ArticlesSchema> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileArticlesAdapter.ProfileArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ProfileArticlesAdapter.ProfileArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileArticlesAdapter.ProfileArticlesViewHolder holder, int position) {
        ArticlesSchema article = articlesArrayList.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText(article.getAuthor());
        holder.articleNumber.setText(String.valueOf(position + 1));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("title", article.getTitle());
                intent.putExtra("author", article.getAuthor());
                intent.putExtra("description", article.getDescription());
                intent.putExtra("content", article.getContent());
                intent.putExtra("url", article.getUrl());
                intent.putExtra("imageUrl", article.getImg());

                context.startActivity(intent);
            }
        });

        holder.editArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditArticleActivity.class);
                intent.putExtra("id", article.get_id());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("author", article.getAuthor());
                intent.putExtra("description", article.getDescription());
                intent.putExtra("content", article.getContent());
                intent.putExtra("url", article.getUrl());
                intent.putExtra("category", article.getCategory());
                context.startActivity(intent);
            }
        });

        deleteArticle(holder, article);
    }

    private void deleteArticle(ProfileArticlesViewHolder holder, ArticlesSchema article) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.red.newsapp", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        String id = article.get_id();
        holder.deleteArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = ApiInitialize.apiCall();
                API api = retrofit.create(API.class);
                Call<ArticlesSchema> call = api.deleteArticle(id, "Bearer " + token);
                call.enqueue(new retrofit2.Callback<ArticlesSchema>() {
                    @Override
                    public void onResponse(Call<ArticlesSchema> call, retrofit2.Response<ArticlesSchema> response) {
                        if (response.isSuccessful()) {
                            articlesArrayList.remove(article);
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context, "Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticlesSchema> call, Throwable t) {
                        Toast.makeText(context, "Hata " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ProfileArticlesViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, articleNumber;
        ImageView editArticle, deleteArticle;
        public ProfileArticlesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.article_title);
            author = itemView.findViewById(R.id.article_author);
            articleNumber = itemView.findViewById(R.id.article_number);
            editArticle = itemView.findViewById(R.id.idBtnEditArticle);
            deleteArticle = itemView.findViewById(R.id.idBtnDeleteArticle);
        }
    }
}