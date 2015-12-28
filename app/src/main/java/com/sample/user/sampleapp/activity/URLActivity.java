package com.sample.user.sampleapp.activity;

import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.sample.user.sampleapp.R;
import com.sample.user.sampleapp.chat.Chat;
import com.sample.user.sampleapp.chat.ChatListAdapter;
import com.sample.user.sampleapp.chat.ChatRoom;
import com.sample.user.sampleapp.chat.ChatRoomAdapter;
import com.sample.user.sampleapp.news.MySQLiteHelper;
import com.sample.user.sampleapp.news.News;
import com.sample.user.sampleapp.news.Sha1Hex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Random;

public class URLActivity extends AppCompatActivity {
    private String mUsername;
    MySQLiteHelper db;

    private String Hashed;
    private String URL;
    private String newsTitle;
    private String imageURL;

    private Firebase mFirebaseRef;
    private ChatRoomAdapter mChatRoomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
        setupUsername();
        mFirebaseRef = new Firebase(FirebaseInterface.FIREBASE_URL).child("chatrooms");
        mChatRoomAdapter = new ChatRoomAdapter(mFirebaseRef.limit(50), this, R.layout.chat_room);


        Bundle bundle = getIntent().getExtras();
        Hashed = bundle.getString("hashed");
        URL = bundle.getString("URL");
        imageURL = bundle.getString("imageURL");
        newsTitle = bundle.getString("newsTitle");
        setTitle(newsTitle);
        WebView webview = (WebView)findViewById(R.id.webView);
        WebSettings set = webview.getSettings();
        webview.setWebViewClient(new WebViewClient());
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        Toast.makeText(this,imageURL,Toast.LENGTH_SHORT).show();
        Log.d("xxx", imageURL);
        try {
            webview.loadUrl(java.net.URLDecoder.decode(URL, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        db = new MySQLiteHelper(this);

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SaveNews savenews = new SaveNews();
                //savenews.tmpNewsTitle = newsTitle;
                //savenews.tmpURL = URL;
                //savenews.tmpimageURL = imageURL;
                //savenews.execute();

                if(db.getNews(Hashed) != null){
                    Toast.makeText(getBaseContext(),"Already Saved",Toast.LENGTH_SHORT).show();
                    return;
                }

                db.addNews(new News(Hashed, URL, newsTitle));
                Toast.makeText(getBaseContext(), "Save Success",Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.chatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChatRoom();
                Toast.makeText(getBaseContext(), "Created Chatroom", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createChatRoom(){
        String input = URL;
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            ChatRoom chatroom = new ChatRoom(Hashed, newsTitle, mUsername);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            Date d = new Date();
            mFirebaseRef.child(Hashed).setValue(chatroom);
            mFirebaseRef.child(Hashed).setPriority(-d.getTime());
        }
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


    private void SaveBitmapToFileCache(Bitmap bitmap, String fileName) {
        File fileCacheItem = new File(getApplicationContext().getFilesDir(), fileName);
        OutputStream out = null;

        try
        {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class SaveNews extends AsyncTask<Void, Void, Void> {

        String tmpURL = "";
        String tmpimageURL = "";
        String tmpNewsTitle = "";

        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if(tmpURL != "") {
                String hashed = Sha1Hex.makeSHA1Hash(URL);
                if(db.getNews(hashed) != null) return null;

                if (tmpimageURL != "") {
                    URL imgURL;
                    try {
                        Log.d("imgURL", tmpimageURL);
                        imgURL = new URL(tmpimageURL);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        Bitmap searchResultImage = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream(), null, options);

                        //SaveBitmapToFileCache(searchResultImage, hashed);
                        db.addNews(new News(hashed, tmpURL, tmpNewsTitle));

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
        }
    }


}
