# NinePhotoLayout

### 介绍
本项目是仿照微信朋友圈中的图片九宫格布局，支持单张图片时，根据图片的长宽比例显示，四张图片时，显示2*2布局，其它显示3*3布局显示；
后续会更新关于图片的其它布局...

### 包含控件
1. NinePhotoLayout
2. TakePhotoLayout


### 引用方式
> compile 'com.hewenyu:PhotoLayout:1.1'

### NinePhotoLayout

![正常布局](https://github.com/hewenyuAndroid/NinePhotoLayout/blob/master/screen/normal.gif)
![列表布局](https://github.com/hewenyuAndroid/NinePhotoLayout/blob/master/screen/listview.gif)

#### 使用方式
1. 在布局中设置相关属性
```
<com.hwy.photolayout.NinePhotoLayout
    android:id="@+id/photoLayout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_below="@id/tvContent"
    app:photoSpanSize="3dp" />
```
2. 设置图片加载引擎（这一步是关键，否则图片无法显示）,图片加载引擎需要实现 `IPhotoEngine` 接口，具体可以参考我这里的 [GlidePhotoEngine](https://github.com/hewenyuAndroid/NinePhotoLayout/blob/master/app/src/main/java/com/hwy/ninephotolayout/engine/GlidePhotoEngine.java) 写法；
```
mPhotoLayout.setPhotoEngine(new GlidePhotoEngine());
```
3. 添加图片路径,支持输入单个url，url数组，url列表，每个方法调用都会清空NinePhotoLayout 里面的ImageView，因此要设置图片只需要调用一次相对应的方法即可；
```
mPhotoLayout.addPhoto("支持单个url");
mPhotoLayout.addPhotos("支持输入多个url，url数组，url集合");
```
4. 设置点击事件监听
```
mPhotoLayout.setOnItemClickListener(new NinePhotoLayout.OnItemClickListener() {
        @Override
        public void onItemClick(int position, View view, List<String> urls) {
            showToast("position = " + position);
        }
    });
```

#### 相关属性
```XML
<declare-styleable name="NinePhotoLayout">
    <!-- 图片之间的间距 -->
    <attr name="photoSpanSize" format="dimension|reference" />
    <!-- 图片点击时是否变暗 -->
    <attr name="photoClickDarken" format="boolean" />
    <!-- 单张图片的时候，长宽的最大值同父布局的比例 -->
    <attr name="singlePhotoRatio" format="float" />
</declare-styleable>
```

### TakePhotoLayout

![takephotoLayout1](https://github.com/hewenyuAndroid/NinePhotoLayout/blob/master/screen/takephotolayout1.gif)
![takephotoLayout2](https://github.com/hewenyuAndroid/NinePhotoLayout/blob/master/screen/takephotolayout2.gif)

#### 使用方式
1. 在布局中设置相关属性
```
 <com.hwy.photolayout.TakePhotoLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#EBEBEB"
    android:padding="15dp"
    app:plColumn="3"
    app:plCrossBgColor="#FF0000"
    app:plCrossLength="5dp"
    app:plCrossRadius="10dp"
    app:plCrossSize="1dp"
    app:plItemSpan="15dp"
    app:plMaxCount="9"
    app:plOffsetX="-2dp"
    app:plOffsetY="2dp" />
```
2. 设置图片加载引擎（同NinePhotoLayout的图片加载引擎，使用的是多张图片的方法）,图片加载引擎需要实现 `IPhotoEngine` 接口，具体可以参考我这里的 [GlidePhotoEngine](https://github.com/hewenyuAndroid/NinePhotoLayout/blob/master/app/src/main/java/com/hwy/ninephotolayout/engine/GlidePhotoEngine.java) 写法；
```
mTakePhoto.setPhotoEngine(new GlidePhotoEngine());
```
3. 设置增加图片方法的监听
```Java
mTakePhoto.setOnTakePhotoClickListener(new TakePhotoLayout.OnTakePhotoClickListener() {
        @Override
        public void takePhoto(int hasCount, int arrowCount, List<String> photos) {
            // 这里调用打开照片库的方法
            // hasCount：已经选择的图片的数量
            // arrowCount: 还可以选择图片的最大数量
        }
    });
```
4. 在onActivityResult()方法中将选择的图片列表设置到布局文件中
```Java
mTakePhoto.addPhotos(单张图片/图片地址列表);
```
5. 设置Item点击事件监听
```
mTakePhoto.setOnItemClickListener(new TakePhotoLayout.OnItemClickListener() {
    @Override
    public void onItemClick(int position, List<String> photos) {
        // 这里操作Item的点击事件
    }
});
```
6. 获取已选择的图片列表
```Java
mTakePhoto.getPhotos();
```

#### 相关属性
```XML
<declare-styleable name="TakePhotoLayout">
    <!-- 图片之间的间距 -->
    <attr name="plItemSpan" format="dimension|reference" />
    <!-- 列数 -->
    <attr name="plColumn" format="integer" />
    <!-- 图片为空时，增加图片按钮的占位图 -->
    <attr name="plEmptyPlaceholder" format="reference" />
    <!-- 图片不为空时，增加图片按钮的占位图 -->
    <attr name="plPlaceholder" format="reference" />
    <!-- 图片选择的最大数量 -->
    <attr name="plMaxCount" format="integer" />
    <!-- 清除按钮的背景色 -->
    <attr name="plCrossBgColor" format="color|reference" />
    <!-- 清除按钮背景的半径 -->
    <attr name="plCrossRadius" format="dimension|reference" />
    <!-- 清除按钮交叉的长度（相对于半径） -->
    <attr name="plCrossLength" format="dimension|reference" />
    <!-- 清除按钮交叉的宽度 -->
    <attr name="plCrossSize" format="dimension|reference" />
    <!-- 清除按钮交叉的颜色 -->
    <attr name="plCrossColor" format="color|reference" />
    <!-- 清除按钮圆心在X轴上的偏移量 -->
    <attr name="plOffsetX" format="dimension|reference" />
    <!-- 清除按钮圆心在Y轴上的偏移量 -->
    <attr name="plOffsetY" format="dimension|reference" />
</declare-styleable>
```