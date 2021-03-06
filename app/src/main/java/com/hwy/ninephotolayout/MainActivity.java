package com.hwy.ninephotolayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void normal(View view) {
        startActivity(new Intent(this, NormalActivity.class));
    }

    public void inListView(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }

    public void inRecyclerView(View view) {
        startActivity(new Intent(this, RecyclerActivity.class));
    }

    public void takePhoto(View view) {
        startActivity(new Intent(this, TakePhotoActivity.class));
    }
}
