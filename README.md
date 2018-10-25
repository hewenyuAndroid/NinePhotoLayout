# NinePhotoLayout

### 介绍
本项目是仿照微信朋友圈中的图片九宫格布局，支持单张图片时，根据图片的长宽比例显示，四张图片时，显示2*2布局，其它显示3*3布局显示；
后续会更新关于图片的其它布局...

### 效果图
![正常布局](https://github.com/hewenyuAndroid/NinePhotoLayout/blob/master/screen/normal.gif)

![列表布局](https://github.com/hewenyuAndroid/NinePhotoLayout/blob/master/screen/listview.gif)

### 引用方式
> compile 'com.hewenyu:PhotoLayout:1.0'

### 使用方式
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

### 相关属性
| 属性        |    |
| --------   | -----  | 
|photoSpanSize     |设置图片的间距 |
|photoClickDarken        |设置图片被触摸时，背景是否变暗  | 
|singlePhotoRatio        |设置当张图片的最大宽/高与父布局的宽度的比例  | 
