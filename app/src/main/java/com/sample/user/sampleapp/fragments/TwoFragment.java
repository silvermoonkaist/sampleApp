package com.sample.user.sampleapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.sample.user.sampleapp.R;
import com.sample.user.sampleapp.activity.ImageActivity;
import com.sample.user.sampleapp.image.ImageAdapter;
import com.sample.user.sampleapp.news.MySQLiteHelper;
import com.sample.user.sampleapp.news.News;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class TwoFragment extends Fragment{
    MySQLiteHelper db;
    List<News> list;
    ArrayList<Bitmap> bitmaps;
    ImageAdapter imgAdapter;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_two, container, false);
        db = new MySQLiteHelper(getContext());


        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh(){
                swipeView.setRefreshing(true);
                bitmaps.clear();
                list.clear();
                list = db.getAllNews();
                for(News n : list){
                    Bitmap b = n.loadImage(getContext());
                    if(b != null)
                        bitmaps.add(b);
                }
                imgAdapter.notifyDataSetChanged();
                swipeView.setRefreshing(false);
            }
        });


        list = db.getAllNews();
        bitmaps = new ArrayList<Bitmap>();
        for(News n : list){
            Bitmap b = n.loadImage(getContext());
            if(b != null)
                bitmaps.add(b);
        }





        GridView gridview = (GridView) rootView.findViewById(R.id.gridView);

        imgAdapter = new ImageAdapter(getContext(), bitmaps);
        imgAdapter.notifyDataSetChanged();
        gridview.setAdapter(imgAdapter);

        // Listening to GridView item click
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Launch ImageViewPager.java on selecting GridView Item
                Intent i = new Intent(getContext(), ImageActivity.class);

                // Show a simple toast message for the item position
                //Toast.makeText(MainActivity.this, "" + position , Toast.LENGTH_SHORT).show();

                // Send the click position to ImageViewPager.java using intent
                i.putExtra("id", position);

                // Start ImageViewPager
                startActivity(i);
            }
        });

        // Inflate the layout for this fragment


        /**
         * CRUD Operations
         * */
        // add Books
        /*
        db.addNews(new MyNews("Android Application Development Cookbook", "Wei Meng Lee"));
        db.addNews(new MyNews("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));
        db.addNews(new MyNews("Learn Android App Development", "Wallace Jackson"));

        // get all books

        // delete one book
        db.deleteNews(list.get(0));
        */


        return rootView;
    }


}
