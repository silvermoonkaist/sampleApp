package com.sample.user.sampleapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Toast;

import com.sample.user.sampleapp.R;
import com.sample.user.sampleapp.image.ImageAdapter;
import com.sample.user.sampleapp.image.ImagePagerAdapter;
import com.sample.user.sampleapp.news.MySQLiteHelper;
import com.sample.user.sampleapp.news.News;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private static final int SWIPE_MIN_DISTANCE = 30;
    private static final int SWIPE_THRESHOLD_VELOCITY = 30;
    private GestureDetector mGestureDetector;
    private static Toast mToast;
    private ImageAdapter imageAdapter;


    int position = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        mToast = Toast.makeText(this, "null", Toast.LENGTH_SHORT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Retrieve data from MainActivity on item click event
        Intent p = getIntent();
        position = p.getExtras().getInt("id");

        MySQLiteHelper db = new MySQLiteHelper(this);
        List<News> list = db.getAllNews();
        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
        for(News n : list){
            Bitmap b = n.loadImage(this);
            if(b != null)
                bitmaps.add(b);
        }

        imageAdapter = new ImageAdapter(this, bitmaps);
        List<ImageView> images = new ArrayList<ImageView>();

        // Retrieve all the images
        for (int i = 0; i < imageAdapter.getCount(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(imageAdapter.getItem(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            images.add(imageView);
        }

        // Set the images into ViewPager
        ImagePagerAdapter pageradapter = new ImagePagerAdapter(images);
        ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(pageradapter);
        // Show images following the position

        mToast.setText("" + (position + 1) + "/" + imageAdapter.getCount());
        mToast.show();
        viewpager.setCurrentItem(position);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Toast.makeText(getApplicationContext(), "" + (position + 1) + "/" + imageAdapter.getCount(), Toast.LENGTH_SHORT).show();
                mToast.setText("" + (position + 1) + "/" + imageAdapter.getCount());
                mToast.show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });

    }

}