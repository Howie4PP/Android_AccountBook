package com.example.shenhaichen.capstone_project_accountbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shenhaichen.capstone_project_accountbook.R;
import com.example.shenhaichen.capstone_project_accountbook.bean.DetailAccount;

import java.util.List;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class DetailAccountAdapter extends RecyclerView.Adapter<DetailAccountAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<DetailAccount> mData;
    private Context context;
    private OnItemClickListener onItemClicklistener;

    public DetailAccountAdapter(List<DetailAccount> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    public void setmData(List<DetailAccount> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        // when setting the template, the main layout_height must set as wrap_content.
        // If set match_content, the recyclerView only show one line of item.
        return new ViewHolder(mInflater.inflate(R.layout.detail_account, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DetailAccount item = mData.get(position);

        holder.category.setText(item.getCategory());
        holder.payment.setText(item.getPayment());
        if (item.getAmount() != null) {
            holder.amount.setText(item.getCurrency() + Double.parseDouble(item.getAmount()));
        } else {
            holder.amount.setText("");
        }
        if ("1".equals(item.getStyle())) {
            holder.amount.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.amount.setTextColor(context.getResources().getColor(R.color.red));
        }

        holder.comment.setText(item.getComment());
        holder.date.setText(item.getDate());
        holder.image.setImageResource(item.getImage());

    }

    @Override
    public int getItemCount() {
        if (mData != null && mData.size() > 0)
            return mData.size();
        return 0;

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView category;
        private TextView payment;
        private TextView amount;
        private TextView comment;
        private TextView date;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            category =  itemView.findViewById(R.id.detail_account_category);
            payment =  itemView.findViewById(R.id.detail_account_payment);
            amount = itemView.findViewById(R.id.detail_account_number);
            comment =  itemView.findViewById(R.id.detail_account_comment);
            date = itemView.findViewById(R.id.detail_account_date);
            image =  itemView.findViewById(R.id.detail_account_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            DetailAccount detailItem = mData.get(getLayoutPosition());
            if (onItemClicklistener != null) {
                onItemClicklistener.onClick(v, detailItem);
            }
        }
    }
    public void setOnItemClicklistener(OnItemClickListener onItemClicklistener) {
        this.onItemClicklistener = onItemClicklistener;
    }

    public interface OnItemClickListener {
        void onClick(View view, DetailAccount detailItem);
    }

}