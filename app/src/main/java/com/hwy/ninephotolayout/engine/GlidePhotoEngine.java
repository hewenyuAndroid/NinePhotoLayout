package com.hwy.ninephotolayout.engine;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hwy.ninephotolayout.R;
import com.hwy.photolayout.IPhotoEngine;

/**
 * 作者: hewenyu
 * 日期: 2018/10/25 10:21
 * 说明:
 */
public class GlidePhotoEngine implements IPhotoEngine {

    @Override
    public void loadImageSingle(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions();
        options.error(R.drawable.placeholder_error);
        options.fitCenter();
        options.placeholder(R.drawable.placeholder_loader);
        Glide.with(imageView)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(imageView)
                .onLoadFailed(imageView.getResources().getDrawable(R.drawable.placeholder_error));
    }

    @Override
    public void loadImageMulti(int position, ImageView imageView, String url) {
        RequestOptions options = new RequestOptions();
        options.error(R.drawable.placeholder_error);
        options.centerCrop();
        options.placeholder(R.drawable.placeholder_loader);
        Glide.with(imageView)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(imageView)
                .onLoadFailed(imageView.getResources().getDrawable(R.drawable.placeholder_error));
    }

}
