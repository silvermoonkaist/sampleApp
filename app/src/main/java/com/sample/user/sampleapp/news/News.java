package com.sample.user.sampleapp.news;

/**
 * Created by user on 2015-12-27.
 */
public class News {
    private String id;
    private String url;
    private String title;


    public News(){}

    public News(String url, String title) {
        super();
        this.id = Sha1Hex.makeSHA1Hash(url);
        this.url = url;
        this.title = title;
    }

    //getters & setters

    @Override
    public String toString() {
        return "News [id=" + id + ", url=" + url
                + ", title=" + title + "]";
    }

    public String getURL() {
        return url;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public void setURL(String url){
        this.url = url;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
