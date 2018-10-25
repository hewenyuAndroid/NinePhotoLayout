package com.hwy.photolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: hewenyu
 * 日期: 2018/10/24 16:06
 * 说明: 图片九宫格布局
 */
public class NinePhotoLayout extends FrameLayout {

    private int mItemSpan = 2;

    private int mItemSize;

    /**
     * 图片点击时是否变暗
     */
    private boolean photoClickDarken = true;

    /**
     * 列数
     */
    private int mColumnCount = 3;

    /**
     * 只有单张图片的时候，ImageView 的 长宽的最大值同父布局的宽度的比例
     */
    private float mChildMaxRatio = 0.6f;

    private List<String> mUrls;

    private IPhotoEngine mPhotoEngine;

    /**
     * ImageView 的最大尺寸，第一次测量的时候赋值，否则会有问题
     */
    private int maxSize = 0;

    public NinePhotoLayout(Context context) {
        this(context, null);
    }

    public NinePhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NinePhotoLayout);
        mItemSpan = typedArray.getDimensionPixelSize(R.styleable.NinePhotoLayout_photoSpanSize, 2);
        photoClickDarken = typedArray.getBoolean(R.styleable.NinePhotoLayout_photoClickDarken, true);
        mChildMaxRatio = typedArray.getFloat(R.styleable.NinePhotoLayout_singlePhotoRatio, 0.6f);
        typedArray.recycle();

        mUrls = new ArrayList<>();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (getChildCount() == 1) {
            // 单张图片
            View view = getChildAt(0);
            if (view instanceof RatioImageView) {
                if (maxSize == 0) {
                    maxSize = (int) (MeasureSpec.getSize(widthMeasureSpec) * mChildMaxRatio);
                }
                int childMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
                RatioImageView imageView = (RatioImageView) view;
                imageView.setRatioMaxSize(maxSize);
                super.onMeasure(childMeasureSpec, childMeasureSpec);

                setMeasuredDimension(view.getMeasuredWidth() + getPaddingLeft() + getPaddingRight(),
                        view.getMeasuredHeight() + getPaddingTop() + getPaddingBottom());
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
            return;
        }

        // 多张图片
        int width = MeasureSpec.getSize(widthMeasureSpec);
        // 计算 ImageView 的宽度
        mItemSize = (width - mItemSpan * (mColumnCount - 1) - getPaddingLeft() - getPaddingRight()) / mColumnCount;

        // 计算行数
        int rowCount = getRowCount();

        // 计算高度
        int height = rowCount * mItemSize + (rowCount - 1) * mItemSpan + getPaddingBottom() + getPaddingTop();

        int childCount = getChildCount();

        if (getChildCount() > 1) {
            int childMeasureSpec = MeasureSpec.makeMeasureSpec(mItemSize, MeasureSpec.EXACTLY);

            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                if (view instanceof RatioImageView) {
                    ((RatioImageView) view).setRatioMaxSize(0);
                }
                // 直接测量
                view.measure(childMeasureSpec, childMeasureSpec);
            }

            setMeasuredDimension(width, height);
        } else {
            // 没有图片则不显示
            setMeasuredDimension(0, 0);
        }

    }

    /**
     * 获取行数
     *
     * @return
     */
    private int getRowCount() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        } else if (childCount == 1) {
            return 1;
        } else {
            int rowCount = childCount / mColumnCount;
            if ((childCount % mColumnCount > 0)) {
                rowCount++;
            }
            return rowCount;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        int childCount = getChildCount();

        int startX = getPaddingLeft();
        int startY = getPaddingTop();

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);

            int endX = startX + view.getMeasuredWidth();
            endX = endX > right - getPaddingRight() ? right - getPaddingRight() : endX;

            int endY = startY + view.getMeasuredHeight();
            endY = endY > bottom - getPaddingBottom() ? bottom - getPaddingBottom() : endY;

            view.layout(startX, startY, endX, endY);

            if (childCount == 4 && i == 1) {
                startX = getPaddingLeft();
                startY += mItemSize + mItemSpan;
                continue;
            } else if (childCount == 4 && i == 2) {
                startX += mItemSize + mItemSpan;
                continue;
            }

            if (i % mColumnCount == (mColumnCount - 1)) {
                startX = getPaddingLeft();
                startY += mItemSize + mItemSpan;
            } else {
                startX += mItemSize + mItemSpan;
            }

        }

    }


    /**
     * 添加图片
     *
     * @param url
     */
    public void addPhoto(String url) {
        removeAllViews();
        mUrls.clear();

        if (TextUtils.isEmpty(url)) {
            return;
        }

        final ImageView imageView = createImageView();
        addView(imageView);
        mUrls.add(url);

        displayImageSingle(imageView, url);

        setItemClickListener();
    }


    /**
     * 添加图片列表
     *
     * @param urls
     */
    public void addPhotos(String... urls) {
        removeAllViews();
        mUrls.clear();

        for (String url : urls) {
            final ImageView imageView = createImageView();
            addView(imageView);

            if (TextUtils.isEmpty(url)) {
                continue;
            }

            mUrls.add(url);

            if (urls.length == 1) {
                displayImageSingle(imageView, url);
            } else {
                displayImageMulti(mUrls.size() - 1, imageView, url);
            }

        }

        setItemClickListener();
    }

    /**
     * 添加图片列表
     *
     * @param urls
     */
    public void addPhotos(List<String> urls) {
        removeAllViews();
        mUrls.clear();

        for (String url : urls) {
            final ImageView imageView = createImageView();
            addView(imageView);

            if (TextUtils.isEmpty(url)) {
                continue;
            }

            mUrls.add(url);

            if (urls.size() == 1) {
                displayImageSingle(imageView, url);
            } else {
                displayImageMulti(mUrls.size() - 1, imageView, url);
            }
        }

        setItemClickListener();
    }

    private void setItemClickListener() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View view = getChildAt(i);
            final int position = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, view, mUrls);
                    }
                }
            });
        }
    }

    private ImageView createImageView() {
        RatioImageView imageView = new RatioImageView(getContext());
        imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setArrowDarken(photoClickDarken);
        return imageView;
    }

    /**
     * 显示多张图片
     *
     * @param position
     * @param imageView
     * @param url
     */
    public void displayImageMulti(int position, ImageView imageView, String url) {
        if (mPhotoEngine != null) {
            mPhotoEngine.loadImageMulti(position, imageView, url);
        }
    }

    /**
     * 显示单张图片
     *
     * @param imageView
     * @param url
     */
    public void displayImageSingle(ImageView imageView, String url) {
        if (mPhotoEngine != null) {
            mPhotoEngine.loadImageSingle(imageView, url);
        }
    }

    /**
     * 设置图片加载引擎
     *
     * @param photoEngine
     */
    public void setPhotoEngine(IPhotoEngine photoEngine) {
        this.mPhotoEngine = photoEngine;
    }

    /**
     * 获取图片加载引擎
     *
     * @return
     */
    public IPhotoEngine getPhotoEngine() {
        return mPhotoEngine;
    }

    // region ------------------ 点击事件回调监听 -----------------------

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position, View view, List<String> urls);

    }

    // endregion --------------------------------------------------------
}
