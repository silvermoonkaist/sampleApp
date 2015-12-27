package com.sample.user.sampleapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.user.sampleapp.R;
import com.sample.user.sampleapp.news.MySQLiteHelper;
import com.sample.user.sampleapp.news.News;

import java.util.Iterator;
import java.util.List;

public class TwoFragment extends Fragment{

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
        // Inflate the layout for this fragment

        MySQLiteHelper db = new MySQLiteHelper(getContext());

        /**
         * CRUD Operations
         * */
        // add Books
        db.addNews(new News("Android Application Development Cookbook", "Wei Meng Lee"));
        db.addNews(new News("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));
        db.addNews(new News("Learn Android App Development", "Wallace Jackson"));

        // get all books
        List<News> list = db.getAllNews();

        // delete one book
        db.deleteNews(list.get(0));

        return inflater.inflate(R.layout.fragment_two, container, false);
    }

}
