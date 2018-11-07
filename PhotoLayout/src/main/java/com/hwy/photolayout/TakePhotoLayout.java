package com.hwy.photolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者: hewenyu
 * 日期: 2018/11/1 19:50
 * 说明: 选择图片的布局
 */
public class TakePhotoLayout extends ViewGroup {

    /**
     * 默认增加图片的占位图
     */
    private static final int DEFAULT_PLACEHOLDER = R.drawable.icon_take_photo;
    /**
     * 默认没有图片时候的占位图
     */
    private static final int DEFAULT_EMPTY_PLACEHOLDER = R.drawable.icon_take_photo_empty;

    /**
     * 默认无效的触摸事件位置
     */
    private static final int INVALID_EVENT = -1;

    /**
     * 选择图片的最大数量
     */
    private int mMaxCount = 9;

    /**
     * 列数
     */
    private int mColumn = 4;

    /**
     * 缓存图片地址的集合
     */
    private List<String> mPhotoPaths;

    /**
     * item的间距
     */
    private int mItemSpan = 10;

    /**
     * 通过计算得到
     */
    private int mItemSize;

    /**
     * 删除按钮的背景颜色
     */
    private int mCrossBgColor = Color.GRAY;

    /**
     * 删除背景的半径
     */
    private int mCrossRadius = 20;

    /**
     * 删除交叉的长度（相对于半径）
     */
    private int mCrossLength;

    /**
     * 删除交叉的宽度
     */
    private int mCrossSize;

    /**
     * 删除交叉的颜色
     */
    private int mCrossColor;

    /**
     * 删除按钮与图片右上角的偏移量
     */
    private int mOffsetX, mOffsetY;

    private Paint mCrossPaint;

    /**
     * 没有图片时添加图片按钮的占位图
     */
    private Drawable mEmptyPlaceholder;

    /**
     * 添加过图片后的的占位图
     */
    private Drawable mPlaceholder;

    /**
     * 默认添加按钮
     */
    private ImageView mDefaultImageView;

    /**
     * 用于保存每张图片的删除按钮的区域，用于设置点击事件
     */
    private List<Rect> mCrossRanges;

    /**
     * 当前触摸的清除按钮对应的图片的位置
     */
    private int mTouchIndex = INVALID_EVENT;

    /**
     * 控件的宽度是否跟着图片的数量变化（不满一行时有效）
     */
    private boolean mAutoWidth;

    public TakePhotoLayout(Context context) {
        this(context, null);
    }

    public TakePhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TakePhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TakePhotoLayout);
        mItemSpan = array.getDimensionPixelSize(R.styleable.TakePhotoLayout_plItemSpan, 10);
        mColumn = array.getInt(R.styleable.TakePhotoLayout_plColumn, 4);
        mEmptyPlaceholder = array.getDrawable(R.styleable.TakePhotoLayout_plEmptyPlaceholder);
        if (mEmptyPlaceholder == null) {
            mEmptyPlaceholder = getResources().getDrawable(DEFAULT_EMPTY_PLACEHOLDER);
        }
        mPlaceholder = array.getDrawable(R.styleable.TakePhotoLayout_plPlaceholder);
        if (mPlaceholder == null) {
            mPlaceholder = getResources().getDrawable(DEFAULT_PLACEHOLDER);
        }
        mMaxCount = array.getInt(R.styleable.TakePhotoLayout_plMaxCount, 9);
        mCrossBgColor = array.getColor(R.styleable.TakePhotoLayout_plCrossBgColor, Color.GRAY);
        mCrossRadius = array.getDimensionPixelSize(R.styleable.TakePhotoLayout_plCrossRadius, 10);
        mCrossLength = array.getDimensionPixelSize(R.styleable.TakePhotoLayout_plCrossLength, 6);
        mCrossSize = array.getDimensionPixelSize(R.styleable.TakePhotoLayout_plCrossSize, 2);
        mCrossColor = array.getColor(R.styleable.TakePhotoLayout_plCrossColor, Color.WHITE);
        mOffsetX = array.getDimensionPixelSize(R.styleable.TakePhotoLayout_plOffsetX, 0);
        mOffsetY = array.getDimensionPixelSize(R.styleable.TakePhotoLayout_plOffsetY, 0);
        mAutoWidth = array.getBoolean(R.styleable.TakePhotoLayout_plAutoWidth, false);
        array.recycle();

        init();

    }

    private void init() {
        mPhotoPaths = new ArrayList<>();

        mCrossPaint = new Paint();
        mCrossPaint.setDither(true);
        mCrossPaint.setAntiAlias(true);
        mCrossPaint.setStyle(Paint.Style.FILL);

        mCrossRanges = new ArrayList<>();
        setClickable(true);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        removeAllViews();
        updatePlaceholder();
    }

    /**
     * 更新添加图片按钮
     */
    private void updatePlaceholder() {

        if (mDefaultImageView == null) {
            mDefaultImageView = new ImageView(getContext());
            mDefaultImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            addView(mDefaultImageView);

            mDefaultImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTakePhotoClickListener != null) {
                        onTakePhotoClickListener.takePhoto(mPhotoPaths.size(), mMaxCount - mPhotoPaths.size(), mPhotoPaths);
                    }
                }
            });

        }

        if (getChildCount() <= 1) {
            mDefaultImageView.setImageDrawable(mEmptyPlaceholder);
        } else {
            mDefaultImageView.setImageDrawable(mPlaceholder);
        }

        if (getChildCount() >= mMaxCount + 1) {
            mDefaultImageView.setVisibility(GONE);
        } else {
            mDefaultImageView.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = getMeasuredWidth();
        int h = 0;

        if (mItemSize == 0) {
            mItemSize = (w - getPaddingLeft() - getPaddingRight() - (mColumn - 1) * mItemSpan) / mColumn;
        }

        int childCount = getChildCount();

        // 计算Item的测量大小
        int childMeasureSpec = MeasureSpec.makeMeasureSpec(mItemSize, MeasureSpec.EXACTLY);

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.measure(childMeasureSpec, childMeasureSpec);
        }

        // 判断是否还可以增加图片，如果超过图片的最大数量，则隐藏添加图片按钮
        if (childCount - 1 >= mMaxCount) {
            childCount--;
        }

        // 如果图片没有超过一行，则父控件缩小
        if (mAutoWidth && childCount < mColumn) {
            w = childCount * mItemSize + getPaddingLeft() + getPaddingRight() + (childCount - 1) * mItemSpan;
        }

        int rowCount = childCount / mColumn;
        if (childCount % mColumn > 0) {
            rowCount++;
        }
        // 计算父控件的实际高度
        h = getPaddingTop() + getPaddingBottom() + (rowCount - 1) * mItemSpan + rowCount * mItemSize;

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        updateTouchRanges();
    }

    /**
     * 更新触摸事件的范围
     */
    private void updateTouchRanges() {
        mCrossRanges.clear();

        int cx = getPaddingLeft() + mItemSize;
        int cy = getPaddingTop();

        int drawCx = 0;
        int drawCy = 0;

        for (int i = 0; i < getChildCount() - 1; i++) {

            drawCx = cx + mOffsetX;
            drawCy = cy + mOffsetY;

            // 计算每个Item的清除按钮绘制的区域
            mCrossRanges.add(
                    new Rect(drawCx - mCrossRadius,
                            drawCy - mCrossRadius,
                            drawCx + mCrossRadius,
                            drawCy + mCrossRadius)
            );

            cx += mItemSpan + mItemSize;

            if ((i + 1) % mColumn == 0) {
                cx = getPaddingLeft() + mItemSize;
                cy += mItemSize + mItemSpan;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();

        int startX = getPaddingLeft();
        int startY = getPaddingTop();

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);

            view.layout(startX, startY, startX + mItemSize, startY + mItemSize);

            startX += mItemSize + mItemSpan;

            if ((i + 1) % mColumn == 0) {
                startX = getPaddingLeft();
                startY += mItemSize + mItemSpan;
            }

        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchIndex = calculateTouchIndex(event);
                if (mTouchIndex != INVALID_EVENT) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mTouchIndex != INVALID_EVENT
                        && mTouchIndex == calculateTouchIndex(event)) {
                    removeView(mTouchIndex);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 计算触摸的位置
     *
     * @param event
     * @return
     */
    private int calculateTouchIndex(MotionEvent event) {

        for (int i = 0; i < mCrossRanges.size(); i++) {
            Rect rect = mCrossRanges.get(i);

            if (event.getX() >= rect.left
                    && event.getX() <= rect.right
                    && event.getY() >= rect.top
                    && event.getY() <= rect.bottom) {

                return i;
            }

        }

        return INVALID_EVENT;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        drawCross(canvas);
    }

    /**
     * 绘制删除按钮
     *
     * @param canvas
     */
    private void drawCross(Canvas canvas) {

        int cx = 0;
        int cy = 0;
        Rect rect = null;

        for (int i = 0; i < mCrossRanges.size(); i++) {

            rect = mCrossRanges.get(i);

            cx = rect.right - mCrossRadius;
            cy = rect.bottom - mCrossRadius;

            mCrossPaint.setColor(mCrossBgColor);
            canvas.drawCircle(cx, cy, mCrossRadius, mCrossPaint);

            mCrossPaint.setColor(mCrossColor);

            canvas.save();
            canvas.rotate(45, cx, cy);
            canvas.drawRect(cx - mCrossSize / 2, cy - mCrossLength, cx + mCrossSize / 2, cy + mCrossLength, mCrossPaint);
            canvas.rotate(-90, cx, cy);
            canvas.drawRect(cx - mCrossSize / 2, cy - mCrossLength, cx + mCrossSize / 2, cy + mCrossLength, mCrossPaint);
            canvas.restore();

        }

        rect = null;

    }

    /**
     * 添加图片
     *
     * @param photos
     */
    public void addPhotos(String... photos) {
        if (photos == null) {
            return;
        }
        addPhotos(Arrays.asList(photos));
    }

    /**
     * 添加图片
     *
     * @param photos
     */
    public void addPhotos(List<String> photos) {

        if (photos == null) {
            return;
        }

        for (int i = 0; i < photos.size(); i++) {
            int count = getChildCount();
            if (count - 1 >= mMaxCount) {
                break;
            }
            ImageView imageView = createImageView();
            addView(imageView, count - 1);
            // 增加图片路径
            mPhotoPaths.add(photos.get(i));
            if (mPhotoEngine != null) {
                mPhotoEngine.loadImageMulti(getChildCount(), imageView, photos.get(i));
            }

        }

        setUpdate();
    }

    /**
     * 新建ImageView
     *
     * @return
     */
    private ImageView createImageView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    /**
     * 设置点击事件监听
     */
    private void initListener() {
        int childCount = getChildCount() - 1;

        for (int i = 0; i < childCount; i++) {
            final int position = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, mPhotoPaths);
                    }
                }
            });
        }
    }

    /**
     * 清除对应位置的图片
     *
     * @param position
     */
    private void removeView(int position) {
        // 删除指定位置上的View
        removeViewAt(position);
        // 移除对应的图片路径
        mPhotoPaths.remove(position);
        setUpdate();

    }

    /**
     * 设置更新
     */
    private void setUpdate() {
        // 更新触摸按钮的范围
        updateTouchRanges();
        // 更新增加图片是否显示以及占位图
        updatePlaceholder();
        // 更新监听事件
        initListener();
        invalidate();
    }

    /**
     * 获取图片路径集合
     *
     * @return
     */
    public List<String> getPhotos() {
        return mPhotoPaths;
    }

    // region ------------ get/set -------------

    /**
     * 设置最大数量
     *
     * @param maxCount
     */
    public void setMaxCount(int maxCount) {
        this.mMaxCount = maxCount;
        int count = getChildCount() - 1;
        for (int i = count - 1; i >= mMaxCount; i--) {
            mPhotoPaths.remove(i);
            removeViewAt(i);
        }
        setUpdate();
    }

    /**
     * 获取最大数量
     *
     * @return
     */
    public int getMaxCount() {
        return this.mMaxCount;
    }

    /**
     * 设置列数
     *
     * @param column
     */
    public void setColumn(int column) {
        this.mColumn = column;
        mItemSize = 0;
        requestLayout();
    }

    public int getColumn() {
        return this.mColumn;
    }

    /**
     * 获取Item的间距
     *
     * @param itemSpan
     */
    public void setItemSpan(int itemSpan) {
        this.mItemSpan = itemSpan;
        mItemSize = 0;
        requestLayout();
    }

    public int getItemSpan() {
        return this.mItemSpan;
    }

    /**
     * 设置清除按钮的背景色
     *
     * @param color
     */
    public void setCrossBgColor(int color) {
        this.mCrossBgColor = color;
        invalidate();
    }

    public int getCrossBgColor() {
        return this.mCrossBgColor;
    }

    /**
     * 设置清除按钮背景的半径
     *
     * @param crossRadius
     */
    public void setCrossRadius(int crossRadius) {
        this.mCrossRadius = crossRadius;
        invalidate();
    }

    public int getCrossRadius() {
        return mCrossRadius;
    }

    /**
     * 设置清除按钮交叉的颜色
     *
     * @param color
     */
    public void setCrossColor(int color) {
        this.mCrossColor = color;
        invalidate();
    }

    public int getCrossColor() {
        return this.mCrossColor;
    }

    /**
     * 设置交叉的长度（相对于半径）
     *
     * @param crossLength
     */
    public void setCrossLength(int crossLength) {
        this.mCrossLength = crossLength;
        invalidate();
    }

    public int getCrossLength() {
        return this.mCrossLength;
    }

    /**
     * 设置交叉的宽度
     *
     * @param crossSize
     */
    public void setCrossSize(int crossSize) {
        this.mCrossSize = crossSize;
        invalidate();
    }

    public int getCrossSize() {
        return this.mCrossSize;
    }

    /**
     * 设置清除按钮X/Y方向的偏移量
     *
     * @param offsetX
     * @param offsetY
     */
    public void setOffset(int offsetX, int offsetY) {
        this.mOffsetX = offsetX;
        this.mOffsetY = offsetY;
        setUpdate();
    }

    public int getOffsetX() {
        return this.mOffsetX;
    }

    public int getOffsetY() {
        return this.mOffsetY;
    }

    // endregion -------------------------------


    // region ------------ 回调函数 ------------

    /**
     * 图片加载引擎
     */
    public IPhotoEngine mPhotoEngine;

    public void setPhotoEngine(IPhotoEngine engine) {
        this.mPhotoEngine = engine;
    }

    public OnTakePhotoClickListener onTakePhotoClickListener;

    public void setOnTakePhotoClickListener(OnTakePhotoClickListener onTakePhotoClickListener) {
        this.onTakePhotoClickListener = onTakePhotoClickListener;
    }

    /**
     * 选择图片的回调监听
     */
    public interface OnTakePhotoClickListener {

        /**
         * @param hasCount   已经加载的数量
         * @param arrowCount 还可以加载的最大数量
         * @param photos     已经加载的图片的路径
         */
        void takePhoto(int hasCount, int arrowCount, List<String> photos);

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position, List<String> photos);

    }

// endregion -------------------------------

}
