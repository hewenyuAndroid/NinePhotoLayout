package com.hwy.photolayout;

import android.widget.ImageView;

/**
 * 作者: hewenyu
 * 日期: 2018/10/25 10:18
 * 说明: 图片加载引擎
 */
public interface IPhotoEngine {

    /**
     * 加载单张图片
     *
     * @param imageView
     * @param url
     */
    void loadImageSingle(ImageView imageView, String url);

    /**
     * 加载多张图片
     *
     * @param position
     * @param imageView
     * @param url
     */
    void loadImageMulti(int position, ImageView imageView, String url);

}
