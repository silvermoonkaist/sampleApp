package com.sample.user.sampleapp.activity;

import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.sample.user.sampleapp.R;
import com.sample.user.sampleapp.chat.Chat;
import com.sample.user.sampleapp.chat.ChatListAdapter;

import java.util.Random;

/**
 * Created by user on 2015-12-27.
 */
public class ChatActivity extends AppCompatActivity {
    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private ChatListAdapter mChatListAdapter;
    private ListView listView;
    private EditText inputText;
    private String chatName;
    private String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();

        Firebase.setAndroidContext(this);
        setupUsername();

        chatName = bundle.getString("name");
        setTitle(chatName);
        chatId = bundle.getString("uid");



        mFirebaseRef = new Firebase(FirebaseInterface.FIREBASE_URL).child("chatdata").child(chatId  );

        listView =  (ListView) findViewById(R.id.listViewChat);
        inputText = (EditText) findViewById(R.id.messageInput);

        // Setup our input methods. Enter key on the keyboard or pushing the send button

        inputText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        mChatListAdapter = new ChatListAdapter(mFirebaseRef.limit(50), this, R.layout.chat_message, mUsername);
        listView.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(getBaseContext(), "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }

    private void setupUsername() {
        SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", null);
        if (mUsername == null) {
            Random r = new Random();
            // Assign a random user name if we don't have one saved.
            mUsername = "JavaUser" + r.nextInt(100000);
            prefs.edit().putString("username", mUsername).commit();
        }
    }

    private void sendMessage() {
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(chat);
            inputText.setText("");
        }
    }
}
