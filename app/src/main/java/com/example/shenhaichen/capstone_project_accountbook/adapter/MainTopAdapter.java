package com.example.shenhaichen.capstone_project_accountbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shenhaichen.capstone_project_accountbook.R;
import com.example.shenhaichen.capstone_project_accountbook.bean.AddingTopItem;

import java.util.List;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class MainTopAdapter extends RecyclerView.Adapter<MainTopAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<AddingTopItem> mData;

    public MainTopAdapter(List<AddingTopItem> myData) {
        this.mData = myData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        // when setting the template, the main layout_height must set as wrap_content.
        // If set match_content, the recyclerView only show one line of item.
        return new ViewHolder(mInflater.inflate(R.layout.main_top_recyclerview_template, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AddingTopItem item = mData.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemNumber.setText(item.getItemNumber());

    }

    @Override
    public int getItemCount() {
        if (mData != null && mData.size() > 0)
            return mData.size();
        return 0;

    }

    public void updateData(List<AddingTopItem> mList){
        //before adding new data, have to clear first, to make sure there is not repeat
        mData.clear();
        mData.addAll(mList);
        this.notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName =  itemView.findViewById(R.id.main_top_item_name);
            itemNumber = itemView.findViewById(R.id.main_top_item_number);
        }

    }

}
