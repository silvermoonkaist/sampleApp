package com.sample.user.sampleapp.image;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sample.user.sampleapp.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    // References to our images in res > drawable
    public ArrayList<Bitmap> mThumbIds;

    public ImageAdapter(Context c, ArrayList<Bitmap> bitmaps) {
        mContext = c;
        mThumbIds = bitmaps;
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Bitmap getItem(int position) {
        return mThumbIds.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {  // If it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(340, 340));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(getItem(position));

        return imageView;
    }


}