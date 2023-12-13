package com.red.newsapp.Services.cloud_database;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.red.newsapp.api_response.Article;
import com.red.newsapp.news_adapters.SavedArticlesAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Firebase_storage {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final Map<String, String> article = new HashMap<>();

    public void createArticle(String documentID,String title, String content, String imageUrl, String newsUrl, String author, String description) {
        if(title == null) title = "";
        if(content == null) content = "";
        if(imageUrl == null) imageUrl = "";
        if(newsUrl == null) newsUrl = "";
        if(author == null) author = "";
        if(description == null) description = "";

        article.put("documentID", documentID);
        article.put("title", title);
        article.put("content", content);
        article.put("imageUrl", imageUrl);
        article.put("newsUrl", newsUrl);
        article.put("author", author);
        article.put("description", description);

        db.collection("articles").document(title).set(article).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Firebase_storage", "DocumentSnapshot successfully written!");
            }
        }).addOnFailureListener(e -> Log.w("Firebase_storage", "Error writing document", e));
    }

    public void deleteArticle(String title) {
        db.collection("articles").document(title).delete();
    }

    public void getAllSavedArticles(SavedArticlesAdapter savedArticlesAdapter, ArrayList<Article> articlesArray) {
        db.collection("articles").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (int i = 0; i < task.getResult().size(); i++) {
                    Article article = new Article();
                    article.setTitle(task.getResult().getDocuments().get(i).get("title").toString());
                    article.setDescription(task.getResult().getDocuments().get(i).get("description").toString());
                    article.setUrlToImage(task.getResult().getDocuments().get(i).get("imageUrl").toString());
                    article.setUrl(task.getResult().getDocuments().get(i).get("newsUrl").toString());
                    article.setAuthor(task.getResult().getDocuments().get(i).get("author").toString());
                    article.setContent(task.getResult().getDocuments().get(i).get("content").toString());
                    articlesArray.add(article);
                }
                savedArticlesAdapter.notifyDataSetChanged();
            } else {
                Log.d("Firebase_storage", "Error getting documents: ", task.getException());
            }
        });
    }
}
