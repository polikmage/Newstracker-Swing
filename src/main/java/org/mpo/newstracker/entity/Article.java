package org.mpo.newstracker.entity;


import java.time.LocalDateTime;



public class Article {
    private Source source;
    private String author;
    private String title;
    private String titleEn;
    private String description;
    private String descriptionEn;
    private String url;
    private String urlToImage;
    private LocalDateTime publishedAt;

    public Article() {
    }

    /**
     *
     * @param source
     * @param author
     * @param title
     * @param titleEn
     * @param description
     * @param descriptionEn
     * @param url
     * @param urlToImage
     * @param publishedAt
     */
    public Article(Source source, String author, String title, String titleEn, String description, String descriptionEn, String url, String urlToImage, LocalDateTime publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.titleEn = titleEn;
        this.description = description;
        this.descriptionEn = descriptionEn;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public boolean isTranslated(){
        if (((titleEn!=null)&&(!titleEn.isEmpty()))||((descriptionEn!=null)&&(!descriptionEn.isEmpty())) ){
            return true;
        }
        return  false;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
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

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
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

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }


    @Override
    public String toString() {
        return "Article{" +
                "source=" + source +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", titleEn='" + titleEn + '\'' +
                ", description='" + description + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
