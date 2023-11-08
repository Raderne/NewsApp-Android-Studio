package com.red.newsapp.news_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.red.newsapp.NewsDetailsActivity;
import com.red.newsapp.R;
import com.red.newsapp.api_response.Article;
import com.red.newsapp.cloud_database.Firebase_storage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SavedArticlesAdapter extends RecyclerView.Adapter<SavedArticlesAdapter.SavedArticlesViewHolder>{
    private ArrayList<Article> articlesArrayList;
    private Context context;

    public SavedArticlesAdapter(ArrayList<Article> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SavedArticlesAdapter.SavedArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_articles_rv_item, parent, false);
        return new SavedArticlesAdapter.SavedArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedArticlesAdapter.SavedArticlesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Article articles = articlesArrayList.get(position);
        holder.bottomCardAuthor.setText(articles.getAuthor());
        holder.bottomCardTitle.setText(articles.getTitle());

        if (articles.getUrlToImage() != null && !articles.getUrlToImage().isEmpty()) {
            Picasso.get().load(articles.getUrlToImage()).into(holder.NewsImage);
        } else {
            Picasso.get().load(R.color.black).into(holder.NewsImage);
        }

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase_storage firebase_storage = new Firebase_storage();
                firebase_storage.deleteArticle(articles.getTitle());
                articlesArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, articlesArrayList.size());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("title", articles.getTitle());
                intent.putExtra("author", articles.getAuthor());
                intent.putExtra("description", articles.getDescription());
                intent.putExtra("url", articles.getUrl());
                intent.putExtra("imageUrl", articles.getUrlToImage());
                intent.putExtra("content", articles.getContent());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class SavedArticlesViewHolder extends RecyclerView.ViewHolder {
        TextView bottomCardAuthor, bottomCardTitle;
        ImageView NewsImage;
        ImageButton deleteBtn;
        public SavedArticlesViewHolder(@NonNull View itemView) {
            super(itemView);

            bottomCardTitle = itemView.findViewById(R.id.idSavedTvTitle);
            bottomCardAuthor = itemView.findViewById(R.id.idSavedTvAuthor);
            NewsImage = itemView.findViewById(R.id.ivSavedNewsItemBg);
            deleteBtn = itemView.findViewById(R.id.ivDelete);
        }
    }
}
