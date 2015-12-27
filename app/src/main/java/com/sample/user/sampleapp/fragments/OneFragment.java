package com.sample.user.sampleapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.sample.user.sampleapp.activity.ChatActivity;

import com.sample.user.sampleapp.R;
import com.sample.user.sampleapp.news.MySQLiteHelper;
import com.sample.user.sampleapp.news.News;

import java.util.ArrayList;

public class OneFragment extends Fragment {

    public OneFragment() {
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_one, container, false);

        MySQLiteHelper db = new MySQLiteHelper(getContext());

        /**
         * CRUD Operations
         * */
        // add Books
        db.addNews(new News("AAAAAAAAAAAAAAAAAAAAAAAAAAA", "xxxxxxxxxxxxxxxx"));
        db.addNews(new News("BBBBBBBBBBBBBBBBBBBBBBBBBBB", "yyyyyyyyyyyyyyyy"));
        db.addNews(new News("CCCCCCCCCCCCCCCCCCCCCCCCCCC", "zzzzzzzzzzzzzzzz"));
        return rootView;
    }

}
