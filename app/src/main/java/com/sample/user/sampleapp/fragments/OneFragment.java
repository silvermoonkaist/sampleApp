package com.sample.user.sampleapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sample.user.sampleapp.R;

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

        ArrayList<String> arrlist = new ArrayList<String>();
        arrlist.add("one");
        arrlist.add("two");
        arrlist.add("three");
        arrlist.add("four");
        arrlist.add("five");

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, arrlist);
        ListView list = (ListView) rootView.findViewById(R.id.listView);
        list.setAdapter(mAdapter);
        
        return rootView;
    }

}
