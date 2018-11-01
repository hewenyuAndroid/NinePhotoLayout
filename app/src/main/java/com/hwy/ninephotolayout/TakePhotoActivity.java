package com.hwy.ninephotolayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.hwy.ninephotolayout.engine.GlidePhotoEngine;
import com.hwy.photolayout.TakePhotoLayout;

import java.util.ArrayList;
import java.util.List;

public class TakePhotoActivity extends AppCompatActivity {

    private TakePhotoLayout mTakePhoto;

    private List<String> mData;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        mContext = this;

        init();

        mTakePhoto = findViewById(R.id.takePhoto);
        mTakePhoto.setPhotoEngine(new GlidePhotoEngine());

        mTakePhoto.setOnTakePhotoClickListener(new TakePhotoLayout.OnTakePhotoClickListener() {
            @Override
            public void takePhoto(int hasCount, int arrowCount, List<String> photos) {
                mTakePhoto.addPhotos(mData.get(hasCount));
            }
        });

        mTakePhoto.setOnItemClickListener(new TakePhotoLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, List<String> photos) {
                Toast.makeText(mContext, "position = " + position, Toast.LENGTH_SHORT).show();
                Log.e("TAG", photos.get(position));
            }
        });

    }

    private void init() {
        mData = new ArrayList<>();
        // 横向
        String url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540445787345&di=6d2e585c1720e9b9f229803400b82fdb&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fa%2F58423377d8fc2.jpg";
        // 纵向
        String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540442072315&di=673f3f34c07ec46b8feb655c85ddea08&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201406%2F24%2F20140624003608_4UseG.jpeg";
        // 纵向
        String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540442072315&di=d0910e2c6475de5a30dcb0855f881e30&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201511%2F29%2F20151129194142_hZzMP.jpeg";
        // 纵向
        String url4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540445745759&di=6a4c4e9d4ab39d68cc7aa77851e8cd9e&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3665312894%2C1788194563%26fm%3D214%26gp%3D0.jpg";
        // 横向
        String url5 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540445828761&di=1e21158be6ae2419ec4610e79d7bce88&imgtype=0&src=http%3A%2F%2Fold.bz55.com%2Fuploads%2Fallimg%2F141210%2F139-141210093229.jpg";
        // 横向
        String url6 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540445873088&di=6799e0be22259c9e6813d86192e67617&imgtype=0&src=http%3A%2F%2Fimg18.3lian.com%2Fd%2Ffile%2F201710%2F10%2F56eff1e0c7eaeca89e3f2f285e34c309.jpg";
        // 横向
        String url7 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540445933719&di=4f832940500ad110294654f2181af899&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F7%2F58772cd5e44ca.jpg";
        // 横向
        String url8 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540445958763&di=2f86e807a741f06eed5cfa56f0a7a1a1&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fe%2F5860d881cc165.jpg";
        // 纵向
        String url9 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540446033835&di=8ee789b0fe287c03aa46304e0034f949&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170715%2F41c7309ea12741acb74335332f1fd326_th.jpg";

        mData.add(url1);
        mData.add(url2);
        mData.add(url3);
        mData.add(url4);
        mData.add(url5);
        mData.add(url6);
        mData.add(url7);
        mData.add(url8);
        mData.add(url9);

    }


}
