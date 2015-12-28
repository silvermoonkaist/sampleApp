package com.sample.user.sampleapp.fragments;

import com.sample.user.sampleapp.activity.FirebaseInterface;
import com.sample.user.sampleapp.chat.ChatRoomAdapter;
import com.sample.user.sampleapp.news.Sha1Hex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import com.sample.user.sampleapp.R;
import com.sample.user.sampleapp.activity.ChatActivity;
import com.sample.user.sampleapp.chat.Chat;
import com.sample.user.sampleapp.chat.ChatListAdapter;

public class ThreeFragment extends Fragment{
    private Firebase mFirebaseRef;
    private ChatRoomAdapter mChatRoomAdapter;

    ListView listView;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_three, container, false);

        Firebase.setAndroidContext(getContext());

        mFirebaseRef = new Firebase(FirebaseInterface.FIREBASE_URL).child("chatrooms");
        mChatRoomAdapter = new ChatRoomAdapter(mFirebaseRef.limit(50), getActivity(), R.layout.chat_room);


        /*
        ArrayList<String> arrlist = new ArrayList<String>();
        arrlist.add("one");
        arrlist.add("two");
        arrlist.add("three");
        arrlist.add("four");
        arrlist.add("five");

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, arrlist);
        */

        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(mChatRoomAdapter);
        mChatRoomAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatRoomAdapter.getCount() - 1);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String name = ((TextView) view.findViewById(R.id.txtText)).getText();
                String name = ((TextView) view.findViewById(R.id.title)).getText().toString();
                String uid = ((TextView) view.findViewById(R.id.uid)).getText().toString();
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("uid", uid);

                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }


}
