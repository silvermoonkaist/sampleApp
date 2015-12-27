package com.sample.user.sampleapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sample.user.sampleapp.R;
import com.sample.user.sampleapp.activity.URLActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class OneFragment extends Fragment {

    TextView textResultTitle;
    TextView textResultPublisher;
    ImageView resultImage;
    ArrayList<News> news_list;
    ListView list;
    NewsAdapter m_adapter;
    ArrayList<NetworkTask> task;

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

        news_list = new ArrayList<News>();
        task = new ArrayList<NetworkTask>();
        list = (ListView) rootView.findViewById(R.id.listView);
        m_adapter = new NewsAdapter(getContext(), R.layout.row, news_list);
        m_adapter.notifyDataSetInvalidated();
        list.setAdapter(m_adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                News temp = (News)parent.getAdapter().getItem(position);
                Intent intent = new Intent(getContext(), URLActivity.class);
                intent.putExtra("URL",temp.getUrl());
                intent.putExtra("imageURL",temp.getImageURL());
                startActivity(intent);
            }
        });

        EditText edittext = (EditText) rootView.findViewById(R.id.editText1);
        edittext.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String inputText = textView.getText().toString();
                    news_list.clear();
                    m_adapter.notifyDataSetInvalidated();
                    if (task != null) {
                        for (int j = 0; j < task.size(); ++j) {
                            if (task.get(j).getStatus().equals(AsyncTask.Status.RUNNING))
                                task.get(j).cancel(true);
                        }
                    }

                    for (int j=0;j<20;++j) {
                        NetworkTask temptask = new NetworkTask();
                        temptask.input = inputText;
                        temptask.num = j;
                        temptask.execute();
                        task.add(temptask);
                    }
                }
                return handled;
            }
        });

        return rootView;
    }

    private class NewsAdapter extends ArrayAdapter<News>{
        ArrayList<News> items;
        public NewsAdapter(Context context, int textViewResourceId, ArrayList<News> items){
            super(context, textViewResourceId, items);
            this.items = items;
        }

        public View getView(int position, View convertView, ViewGroup parent){

            final Context context = parent.getContext();
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row, null);
            }
            News p = items.get(position);
            if (p != null) {
                ImageView im = (ImageView) v.findViewById(R.id.imageView2);
                TextView tt = (TextView) v.findViewById(R.id.toptext);
                TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                if (im != null){
                    im.setImageBitmap(p.getImage());
                }
                if (tt != null){
                    tt.setText(p.getTitle());
                }
                if(bt != null){
                    bt.setText(p.getPublish());
                }
            }
            return v;
        }
    }

    public class News {
        private String title;
        private String publish;
        private String url;
        private Bitmap image;
        private String imageURL;

        public News(String title, String publish, String url, Bitmap image, String imageURL){
            this.title = title;
            this.publish = publish;
            this.url = url;
            this.image = image;
            this.imageURL = imageURL;
        }

        public String getTitle(){
            return title;
        }

        public String getPublish(){
            return publish;
        }

        public String getUrl(){
            return url;
        }

        public Bitmap getImage(){
            return image;
        }

        public String getImageURL() {return imageURL;}
    }

    private class NetworkTask extends AsyncTask<Void, Void, Void> {

        String searchResultTitle = "";
        String searchResultPublisher = "";
        String searchResultURL = "";
        Bitmap searchResultImage = null;
        String searchResultImageURL = "";
        int num=0;
        String input;

        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                URL url = null;
                try {
                    url = new URL("https://ajax.googleapis.com/ajax/services/search/news?" +
                                "v=1.0&q=" + input + "&userip=INSERT-USER-IP" + "&start=" + String.valueOf(num));//barack%20obama
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                URLConnection connection = null;
                try {
                    connection = url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    JSONObject json = new JSONObject(builder.toString());
                    JSONObject responseData = json.getJSONObject("responseData");
                    JSONArray jsonarray = responseData.getJSONArray("results");
                    JSONObject result;
                    JSONObject jsonimage;
                    result = jsonarray.getJSONObject(0);
                    if (result.has("title"))
                        searchResultTitle = html2text(result.optString("title").toString());
                    if (result.has("publisher"))
                        searchResultPublisher = html2text(result.optString("publisher").toString());
                    if (result.has("url"))
                        searchResultURL = result.optString("url").toString();
                    if (result.has("image")) {
                        jsonimage = result.getJSONObject("image");
                        searchResultImageURL = jsonimage.optString("url").toString();
                        url = new URL(jsonimage.optString("url").toString());
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        searchResultImage = BitmapFactory.decodeStream(url.openConnection().getInputStream(),null,options);
                    }
                    if(isCancelled())
                        return null;
                    news_list.add(new News(searchResultTitle, searchResultPublisher,searchResultURL, searchResultImage, searchResultImageURL));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            m_adapter.notifyDataSetChanged() ;
            super.onPostExecute(result);
        }
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
