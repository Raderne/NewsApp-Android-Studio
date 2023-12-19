package com.red.newsapp.Services.Articles;

public class ArticlesSchema {
    private String _id;
    private String author;
    private String title;
    private String description;
    private String content;
    private String url;
    private String img;
    private String category;
    private String userId;

    public ArticlesSchema(String _id, String author, String title, String description, String content, String url, String img, String category) {
        this._id = _id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.content = content;
        this.url = url;
        this.img = img;
        this.category = category;
    }

    public ArticlesSchema(String title, String description, String content, String url, String img, String category) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.url = url;
        this.img = img;
        this.category = category;
    }

    public String get_id() {
        return _id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public String getCategory() {
        return category;
    }

    public String getUserId() {
        return userId;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
