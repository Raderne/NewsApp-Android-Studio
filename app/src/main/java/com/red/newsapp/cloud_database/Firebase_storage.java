package com.red.newsapp.cloud_database;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.red.newsapp.api_response.GONDER;

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

    
}
