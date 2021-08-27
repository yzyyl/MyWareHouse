package com.example.navigationbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationbar.adapter.ViewPagerAdatper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    //底部三个LinearLayout
    private LinearLayout ll_search;
    private LinearLayout ll_shoppingmall;
    private LinearLayout ll_my;
    //底部三个Imageview
    private ImageView iv_search;
    private ImageView iv_shoppingmall;
    private ImageView iv_my;
    //底部三个菜单标题
    private TextView tv_search;
    private TextView tv_shoppingmall;
    private TextView tv_my;

    //搜一搜输入框
    private  TextView tv_search_input;
    //中间内容区域
    private ViewPager viewPager;
    //viewpager适配器
    private ViewPagerAdatper adapter;

    private List<View> views;

    //是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useThemestatusBarColor = true;
    //是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
    protected boolean useStatusBarColor = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //实现沉浸式状态栏
        setStatusBar();
        //加载视图
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //初始化底部按钮事件
        initEvent();
        //设置搜一搜为默认选中
          searchRestart();
    }

    private void initEvent() {
        //设置按钮监听
        ll_search.setOnClickListener(this);
        ll_shoppingmall.setOnClickListener(this);
        ll_my.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_search_input.setOnClickListener(this);
        //viewpager滑动监听
        viewPager.addOnPageChangeListener(this);
    }

    //获取控件对象
    private void initView() {
        ll_search = findViewById(R.id.ll_search);
        ll_shoppingmall = findViewById(R.id.ll_shoppingmall);
        ll_my = findViewById(R.id.ll_my);

        iv_search = findViewById(R.id.iv_search);
        iv_shoppingmall = findViewById(R.id.iv_shoppingmall);
        iv_my = findViewById(R.id.iv_my);

        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_shoppingmall = (TextView) findViewById(R.id.tv_shoppingmall);
        tv_my = (TextView) findViewById(R.id.tv_my);

        this.viewPager = (ViewPager) findViewById(R.id.vp_content);

        // 适配器
        View page_01 = View.inflate(MainActivity.this, R.layout.page_01, null);
        View page_02 = View.inflate(MainActivity.this, R.layout.page_02, null);
        View page_03 = View.inflate(MainActivity.this, R.layout.page_03, null);

        tv_search_input = page_01.findViewById(R.id.tv_search_input);

        views = new ArrayList<View>();
        views.add(page_01);
        views.add(page_02);
        views.add(page_03);
        this.adapter = new ViewPagerAdatper(views);
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        //调用方法，将图片和文字颜色设置为初始颜色
        restartBotton();
        switch (v.getId()) {
            case R.id.ll_search:
                iv_search.setSelected(true);
                tv_search.setSelected(true);
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_shoppingmall:
                iv_shoppingmall.setSelected(true);
                tv_shoppingmall.setSelected(true);
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_my:
                iv_my.setSelected(true);
                tv_my.setSelected(true);
                viewPager.setCurrentItem(2);
                Intent intent=new Intent(MainActivity.this,MyCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_search_input :
//                Toast.makeText(this,"来了",Toast.LENGTH_LONG);
                Intent intent1=new Intent(this,SearchActivity.class);
                startActivity(intent1);
                    break;
            default:
                break;
        }
    }

    //设置搜一搜为默认选中
    private void searchRestart() {
        iv_search.setSelected(true);
        tv_search.setSelected(true);
    }

    //将图片和文字设置为初始默认颜色
    private void restartBotton() {
        iv_search.setSelected(false);
        iv_shoppingmall.setSelected(false);
        iv_my.setSelected(false);

        tv_search.setSelected(false);
        tv_shoppingmall.setSelected(false);
        tv_my.setSelected(false);


    }

    //沉浸式状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorsearch));//设置状态栏背景色 暂不知颜色
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
            Toast.makeText(this, "低于4.4的android系统版本不存在沉浸式状态栏", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        restartBotton();
        //当前view被选择的时候,改变底部菜单图片，文字颜色
        switch (position) {
            case 0:
                iv_search.setSelected(true);
                tv_search.setSelected(true);
                break;
            case 1:
                iv_shoppingmall.setSelected(true);
                tv_shoppingmall.setSelected(true);
                break;
            case 2:
                iv_my.setSelected(true);
                tv_my.setSelected(true);
                break;

            default:
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
