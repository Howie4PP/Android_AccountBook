package com.example.shenhaichen.capstone_project_accountbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shenhaichen.capstone_project_accountbook.R;
import com.example.shenhaichen.capstone_project_accountbook.bean.SpinnerItems;

import java.util.List;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class SpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private List<SpinnerItems> mList;
    private ImageView imageView;
    private TextView textView;


    public SpinnerAdapter(Context mContext, List<SpinnerItems> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(R.layout.spinner_layout, null);
        if (convertView != null) {
            imageView = (ImageView) convertView.findViewById(R.id.spinner_img);
            textView = (TextView) convertView.findViewById(R.id.spinner_item);
            imageView.setImageResource(mList.get(position).getImage());
            textView.setText(mList.get(position).getItemName());
        }

        return convertView;
    }
}

