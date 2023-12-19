package com.red.newsapp.news_adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.red.newsapp.NewsDetailsActivity;
import com.red.newsapp.R;
import com.red.newsapp.Services.Articles.ArticlesSchema;

import java.util.ArrayList;

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
                // TODO: add click listener
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

        // TODO: add edit and delete article functionality
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