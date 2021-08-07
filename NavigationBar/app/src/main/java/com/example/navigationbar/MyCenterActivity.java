package com.example.navigationbar;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyCenterActivity extends AppCompatActivity {
    ImageView blurImageView;
    ImageView avatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载视图
        setContentView(R.layout.activity_mycenter);
        head();

    }
    private  void head()
    {
        //实现个人中心头部磨砂布局
        blurImageView= findViewById(R.id.h_back);
        avatarImageView = findViewById(R.id.h_head);
        Glide.with(this).load(R.drawable.head).bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this)).into(blurImageView);
        Glide.with(this).load(R.drawable.head).bitmapTransform(new CropCircleTransformation(this)).into(avatarImageView);
    }




}
