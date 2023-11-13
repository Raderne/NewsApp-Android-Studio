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
import com.red.newsapp.api_response.Article;
import com.red.newsapp.NewsDetailsActivity;
import com.red.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopHeadlinesNewsRVAdapter extends RecyclerView.Adapter<TopHeadlinesNewsRVAdapter.ViewHolder> {
    private ArrayList<Article> articlesArrayList;
    private Context context;

    public TopHeadlinesNewsRVAdapter(ArrayList<Article> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TopHeadlinesNewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_news_rv_item, parent, false);
        return new TopHeadlinesNewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopHeadlinesNewsRVAdapter.ViewHolder holder, int position) {
        Article articles = articlesArrayList.get(position);
        holder.cardAuthor.setText(articles.getAuthor());
        holder.cardTitle.setText(articles.getTitle());

        if (articles.getUrlToImage() != null && !articles.getUrlToImage().isEmpty()) {
            Picasso.get().load(articles.getUrlToImage()).into(holder.topHeadlinesNewsImage);
        } else {
            Picasso.get().load(R.color.black).into(holder.topHeadlinesNewsImage);
        }

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

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cardAuthor, cardTitle;
        ImageView topHeadlinesNewsImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardAuthor = itemView.findViewById(R.id.tvAuthor);
            cardTitle = itemView.findViewById(R.id.tvTitle);
            topHeadlinesNewsImage = itemView.findViewById(R.id.ivTopNewsItemBg);
        }
    }
}
