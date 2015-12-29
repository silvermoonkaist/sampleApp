package com.sample.user.sampleapp.fragments;


import android.content.Context;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.user.sampleapp.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class TwoFragment extends Fragment{
    ImageView imageview;

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
        // Inflate the layout for this fragment

        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        imageview = (ImageView) rootView.findViewById(R.id.imageView3);

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperation();
            }
        });

        return rootView;
    }

    protected void myUpdateOperation(){
        imageview.setImageResource(R.drawable.piano);
        Toast.makeText(getContext(),"updated!",Toast.LENGTH_SHORT).show();
    }
}
