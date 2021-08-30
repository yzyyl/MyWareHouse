package com.example.navigationbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationbar.adapter.RecyclerAdatper;
import com.example.navigationbar.bean.SearchItemBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private EditText editText;//输入框
    private RecyclerView recyclerView;//
    private Context context = SearchActivity.this;
    private List<SearchItemBean> searchItemBeanList;//集合
    private List<SearchItemBean> list;
    private RecyclerAdatper mAdapter;//
    private LinearLayoutManager mLayoutManager;
    private String mContent;//json字符串
    private String key;//输入的字
    //    private String encodeKey;//输入字符经过encode转码
    private String searchUrl;//访问的URL

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initview();//获取控件对象
        initEvents();//为editText添加监听
    }

    //获取控件对象
    private void initview() {
        recyclerView = findViewById(R.id.rc_view);
        editText = findViewById(R.id.et_search_input);
    }

    //recyclerview
    private void initrecyclerview(List<SearchItemBean> list, String key) {
        if (list == null || list.size() == 0)
            return;
//        searchItemBeanList.clear();
//        searchItemBeanList.addAll(list);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdatper(this, list, key);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

    //为editText添加监听
    private void initEvents() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.i(TAG, "beforeTextChanged: 输入过程中执行该方法");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.i(TAG, "onTextChanged: 输入前确认执行该方法");
            }

            @Override
            public void afterTextChanged(Editable s) {
//                Log.i(TAG, "afterTextChanged: 输入结束执行该方法");
                Toast.makeText(SearchActivity.this, "dfds", Toast.LENGTH_SHORT).show();
                synchro();

            }
        });

    }


    //给recyclerview赋值死数据
    private List<SearchItemBean> getData() {
        List<SearchItemBean> list = new ArrayList<SearchItemBean>();
        SearchItemBean searchItemBean = null;
        for (int i = 0; i < 40; i++) {
            searchItemBean = new SearchItemBean(i, "I am i:" + i);

            list.add(searchItemBean);
        }
        return list;
    }

    //解析 json数据
    public List<SearchItemBean> readJSONContent(String jsonData) {
        List<SearchItemBean> list = new ArrayList<SearchItemBean>();
        SearchItemBean searchItemBean = null;
        try {
//            StringBuffer sb = new StringBuffer(); //创建字符串对象
            JSONObject rootObject = new JSONObject(jsonData);//获取json对象
            JSONArray gArray = rootObject.getJSONArray("g");//获取g对应的对象数组
            for (int i = 0; i < gArray.length(); i++) {
                JSONObject jsonObject = gArray.getJSONObject(i);
                searchItemBean = new SearchItemBean(i, jsonObject.getString("q"));
                list.add(searchItemBean);
            }
            return list;
//            return sb.toString();
        } catch (JSONException e) {
            Log.e("JSONException错误", "readContent: " + e.toString());
            return list;//返回空值
        }
    }

    /**
     * 同步请求
     */
    public void synchro() {
        key = editText.getText().toString();//获取输入内容
        searchUrl = new String("https://m.baidu.com/sugrec?json=1&prod=wise&from=wise_web&net=1&os=2&wd=");
        try {
//            encodeKey = java.net.URLEncoder.encode(key, "utf-8");
            searchUrl = searchUrl + java.net.URLEncoder.encode(key, "utf-8");
            ;//拼接后的字符串
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();//创建单例
                Request request = new Request.Builder()//创建请求
                        .url(searchUrl)
                        .build();
//                "https://m.baidu.com/sugrec?json=1&prod=wise&from=wise_web&net=1&os=2&wd=%E6%B5%8B"
                try {
                    Response response = okHttpClient.newCall(request).execute();//执行请求
                    mContent = response.body().string();//得到返回响应，注意response.body().string() 只能调用一次！
                    list = readJSONContent(mContent);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initrecyclerview(list, key);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("OkHttpActivity", e.toString());
                }
            }
        });
        thread.start();
    }
}
