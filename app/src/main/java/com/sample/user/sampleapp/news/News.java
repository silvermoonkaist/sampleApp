package com.sample.user.sampleapp.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by user on 2015-12-27.
 */
public class News {
    private String id;
    private String url;
    private String title;


    public News(){}

    public News(String id, String url, String title) {
        super();
        this.id = id;
        this.url = url;
        this.title = title;
    }

    //getters & setters

    @Override
    public String toString() {
        return "MyNews [id=" + id + ", url=" + url
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

    public Bitmap loadImage(Context context)
    {
        Bitmap b = null;
        try {
            File f=new File(context.getFilesDir(), this.id);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return b;
    }
}
