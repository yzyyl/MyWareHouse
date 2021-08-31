package com.example.navigationbar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbar.R;
import com.example.navigationbar.bean.SearchItemBean;
import com.example.navigationbar.util.StringFormatUtil;

import java.util.List;

public class RecyclerAdatper extends RecyclerView.Adapter<RecyclerAdatper.MyViewHolder> {
    private List<SearchItemBean> mylist;
    private String mkey;
    //  private  LayoutInflater mInflater;
    private Context context;

    public RecyclerAdatper(Context context, List<SearchItemBean> list, String key) {

        this.context = context;
        mylist = list;
        mkey = key;
//        notifyData(list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SearchItemBean searchItemBean = mylist.get(position);
//        holder.textView.setText(searchItemBean.getText());
//不太懂
        @SuppressLint("ResourceType") SpannableString s= StringFormatUtil.getHighLightKeyWord(context.getString(R.color.babyblue),searchItemBean.getText(),mkey);
            if (s != null)
                holder.textView.setText(s);
            else holder.textView.setText(searchItemBean.getText());

    }
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position,List<SearchItemBean> payloads) {
//        SearchItemBean searchItemBean = mylist.get(position);
//        holder.textView.setText(searchItemBean.getText());
//    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    //更新数据
    public void notifyData(List<SearchItemBean> list) {
        if (list != null) {
            mylist.clear();
            mylist.addAll(list);
            notifyItemRangeChanged(0, list.size());
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.tv_search_item);
        }
    }

}
