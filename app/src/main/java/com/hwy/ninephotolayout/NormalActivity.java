package com.hwy.ninephotolayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hwy.log.LogUtil;
import com.hwy.ninephotolayout.engine.GlidePhotoEngine;
import com.hwy.photolayout.NinePhotoLayout;

import java.util.List;

public class NormalActivity extends AppCompatActivity {

    private NinePhotoLayout mPhotoLayout1;
    private NinePhotoLayout mPhotoLayout2;
    private NinePhotoLayout mPhotoLayout3;
    private NinePhotoLayout mPhotoLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        mPhotoLayout1 = findViewById(R.id.photoLayout1);
        mPhotoLayout2 = findViewById(R.id.photoLayout2);
        mPhotoLayout3 = findViewById(R.id.photoLayout3);
        mPhotoLayout4 = findViewById(R.id.photoLayout4);

        mPhotoLayout1.setPhotoEngine(new GlidePhotoEngine());
        mPhotoLayout2.setPhotoEngine(new GlidePhotoEngine());
        mPhotoLayout3.setPhotoEngine(new GlidePhotoEngine());
        mPhotoLayout4.setPhotoEngine(new GlidePhotoEngine());


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

        mPhotoLayout1.addPhoto(url7);
        mPhotoLayout2.addPhoto(url9);
        mPhotoLayout3.addPhotos(url9, url1, url2, url3);
        mPhotoLayout4.addPhotos(url1, url2, url3, url4, url5, url6, url7, url8, url9);

        initListener();

    }

    private void initListener() {
        mPhotoLayout1.setOnItemClickListener(new NinePhotoLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, List<String> urls) {
                showToast("position = " + position);
            }
        });
        mPhotoLayout2.setOnItemClickListener(new NinePhotoLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, List<String> urls) {
                showToast("position = " + position);
            }
        });
        mPhotoLayout3.setOnItemClickListener(new NinePhotoLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, List<String> urls) {
                showToast("position = " + position);
            }
        });
        mPhotoLayout4.setOnItemClickListener(new NinePhotoLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, List<String> urls) {
                showToast("position = " + position);
            }
        });
    }

    private void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

}
