package com.hwy.photolayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 作者: hewenyu
 * 日期: 2018/10/24 18:11
 * 说明:
 */
public class RatioImageView extends ImageView {

    /**
     * 只有单张图片的时候，ImageView 长宽的最大值
     */
    private float mRatioMaxSize = 0f;

    /**
     * 点击是是否可以变暗
     */
    private boolean arrowDarken;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mRatioMaxSize <= 0) {
            return;
        }

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width > height) {
            height = (int) (height * mRatioMaxSize) / width;
            width = (int) mRatioMaxSize;
        } else if (height > width) {
            width = (int) (width * mRatioMaxSize) / height;
            height = (int) mRatioMaxSize;
        } else {
            width = (int) mRatioMaxSize;
            height = (int) mRatioMaxSize;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setClickable(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Drawable drawable = getDrawable();
                if (arrowDarken && drawable != null) {
                    drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Drawable drawableUp = getDrawable();
                if (arrowDarken && drawableUp != null) {
                    drawableUp.mutate().clearColorFilter();
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    public void setRatioMaxSize(float size) {
        this.mRatioMaxSize = size;
    }

    public void setArrowDarken(boolean arrowDarken) {
        this.arrowDarken = arrowDarken;
    }

}
