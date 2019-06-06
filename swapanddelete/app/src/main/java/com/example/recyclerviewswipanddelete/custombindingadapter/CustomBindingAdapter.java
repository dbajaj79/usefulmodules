package com.example.recyclerviewswipanddelete.custombindingadapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CustomBindingAdapter {

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView,String url)
    {
        Glide.with(imageView).load(url).into(imageView);
    }
}
