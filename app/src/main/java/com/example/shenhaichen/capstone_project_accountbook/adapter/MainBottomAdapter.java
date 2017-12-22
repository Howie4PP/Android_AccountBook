package com.example.shenhaichen.capstone_project_accountbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shenhaichen.capstone_project_accountbook.R;
import com.example.shenhaichen.capstone_project_accountbook.bean.AddingBottomItem;

import java.util.List;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class MainBottomAdapter extends RecyclerView.Adapter<MainBottomAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<AddingBottomItem> mData;
    private OnItemClickListener onItemClicklistener;

    public MainBottomAdapter(List<AddingBottomItem> mDatas) {
        this.mData = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.main_bottom_recyclerview_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AddingBottomItem item = mData.get(position);

        holder.itemName.setText(item.getItemName());
        holder.itemNumberUp.setText(item.getItemNumberUp());
        holder.itemNumberDown.setText(item.getItemNumberDown());
        holder.imageView.setImageResource(item.getImgId());

    }

    @Override
    public int getItemCount() {
        if (mData != null && mData.size() > 0)
            return mData.size();
        return 0;
    }

    public void updateData(String[][] data) {

        // because the speed of refresh, so give up to use the for loop to update data
        mData.get(0).setItemNumberDown(data[0][1]);
        mData.get(0).setItemNumberUp(data[0][0]);
        mData.get(1).setItemNumberDown(data[1][1]);
        mData.get(1).setItemNumberUp(data[1][0]);
        mData.get(2).setItemNumberDown(data[2][1]);
        mData.get(2).setItemNumberUp(data[2][0]);
        mData.get(3).setItemNumberDown(data[3][1]);
        mData.get(3).setItemNumberUp(data[3][0]);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemName;
        private TextView itemNumberUp;
        private TextView itemNumberDown;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.main_bottom_item_name);
            itemNumberUp = (TextView) itemView.findViewById(R.id.main_bottom_item_income);
            itemNumberDown = (TextView) itemView.findViewById(R.id.main_bottom_item_outcome);
            imageView = (ImageView) itemView.findViewById(R.id.main_bottom_item_img1);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            System.out.println("bottom layout position:"+getLayoutPosition());
            if (onItemClicklistener != null) {
                onItemClicklistener.onClick(v, getLayoutPosition());
            }
        }
    }

    public void setOnItemClicklistener(OnItemClickListener onItemClicklistener) {
        this.onItemClicklistener = onItemClicklistener;
    }

    /**
     *  Customize a interface that sending value out
     */
    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

}
