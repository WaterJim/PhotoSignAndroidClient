package com.waterchen.android_photosignapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.extra.db.beans.OfflineBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/5.
 */
public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.ItemViewHolder> {

    private Context mContext;
    private List<OfflineBean> mRecordList;

    private OnItemClickListener mListener;

    public OfflineAdapter(Context context) {
        this(context, new ArrayList<OfflineBean>());
    }

    public OfflineAdapter(Context context, List<OfflineBean> data) {
        this.mContext = context;
        this.mRecordList = data;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void addRecord(List<OfflineBean> data) {
        if (data != null) {
            mRecordList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void removeRecord(int position) {
        if (mRecordList != null) {
            mRecordList.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.layout_offline_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        OfflineBean record = mRecordList.get(position);
        if (record != null) {
            holder.nameTv.setText(record.classname);
            holder.idTv.setText("ID:" + record.classid);
            holder.countTv.setText(record.count + "人");
            holder.dateTv.setText("记录日期:" + record.date);
        }
        if (mListener != null) {
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        TextView nameTv;
        TextView dateTv;
        TextView countTv;
        TextView idTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            nameTv = (TextView) rootView.findViewById(R.id.offline_item_name_tv);
            dateTv = (TextView) rootView.findViewById(R.id.offline_item_date_tv);
            countTv = (TextView) rootView.findViewById(R.id.offline_item_count_tv);
            idTv = (TextView) rootView.findViewById(R.id.offline_item_id_tv);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
