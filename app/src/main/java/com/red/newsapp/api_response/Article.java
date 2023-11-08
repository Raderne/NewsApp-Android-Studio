package com.red.newsapp.api_response;

public class Article {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String content;

    public Article(String author, String title, String description, String url, String urlToImage, String content) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.content = content;
    }

    public Article() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

//{
//        "status": "ok",
//        "totalResults": 35,
//        "articles": [
//            {
//                "source": {
//                "id": null,
//                "name": "CNBC"
//                },
//                "author": "Lee Ying Shan",
//                "title": "Russia's Putin speaks at China's Belt and Road forum in rare international appearance - CNBC",
//                "description": "Russian President Vladimir Putin said the Belt and Road idea \"folds logically within multilateral efforts\" to increase global cooperation.",
//                "url": "https://www.cnbc.com/2023/10/18/russians-vladimir-putin-speaks-at-chinas-belt-and-road-forum-.html",
//                "urlToImage": "https://image.cnbcfm.com/api/v1/image/107318903-1697602700881-gettyimages-1730510609-AFP_33YK49A.jpeg?v=1697603878&w=1920&h=1080",
//                "publishedAt": "2023-10-18T03:50:00Z",
//                "content": "Russia's President Vladimir Putin and Chinese President Xi Jinping walk side by side to a group photo session during the third Belt and Road Forum for International Cooperation at the Great Hall of t… [+3075 chars]"
//            },
//        ]
//}