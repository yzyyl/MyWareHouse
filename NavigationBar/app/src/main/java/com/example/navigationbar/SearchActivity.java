package com.example.navigationbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.example.navigationbar.adapter.RecyclerAdatper;
import com.example.navigationbar.bean.SearchItemBean;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText editText;
    private RecyclerView recyclerView;
    private List<SearchItemBean> list;

    private RecyclerAdatper mAdapter;
    private LinearLayoutManager mLayoutManager;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        list = getData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rc_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RecyclerAdatper(this,list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    private List<SearchItemBean> getData() {
        List<SearchItemBean> list = new ArrayList<SearchItemBean>();
        SearchItemBean searchItemBean = null;
        for (int i = 0; i < 40; i++) {
            searchItemBean = new SearchItemBean(i, "I am i:" + i);

            list.add(searchItemBean);
        }
        return list;
    }
}
