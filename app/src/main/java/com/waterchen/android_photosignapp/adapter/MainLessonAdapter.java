package com.waterchen.android_photosignapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.model.entity.LessonInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/5.
 */
public class MainLessonAdapter extends RecyclerView.Adapter<MainLessonAdapter.ItemViewHolder> {

    private Context mContext;
    private List<LessonInfoEntity.LessonInfo> mLessonList;
    private OnItemClickListener mListener;

    public MainLessonAdapter(Context context) {
        this(context, new ArrayList<LessonInfoEntity.LessonInfo>());
    }

    public MainLessonAdapter(Context context, List<LessonInfoEntity.LessonInfo> classes) {
        this.mContext = context;
        this.mLessonList = classes;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void addLessonInfo(LessonInfoEntity.LessonInfo lessonInfo) {
        if (lessonInfo != null) {
            this.mLessonList.add(lessonInfo);
            notifyDataSetChanged();
        }
    }

    public void addLessonInfo(List<LessonInfoEntity.LessonInfo> lessonInfo) {
        if (lessonInfo != null) {
            this.mLessonList.clear();
            this.mLessonList.addAll(lessonInfo);
            notifyDataSetChanged();
        }
    }

    public void cleanLessonInfo() {
        this.mLessonList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.layout_main_classinfo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        LessonInfoEntity.LessonInfo lessonInfo = mLessonList.get(position);
        holder.nameTv.setText(lessonInfo.name);
        holder.weekTv.setText("每周 " + lessonInfo.week);
        holder.countTv.setText(lessonInfo.count + "人");
        holder.idTv.setText("ID:" + lessonInfo.classid);
        if (mListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mLessonList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        CardView cardView;
        TextView nameTv;
        TextView weekTv;
        TextView lastTv;
        TextView countTv;
        TextView idTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            cardView = (CardView) rootView.findViewById(R.id.mainlist_item_card);
            nameTv = (TextView) rootView.findViewById(R.id.mainlist_item_name_tv);
            weekTv = (TextView) rootView.findViewById(R.id.mainlist_item_week_tv);
            lastTv = (TextView) rootView.findViewById(R.id.mainlist_item_last_tv);
            countTv = (TextView) rootView.findViewById(R.id.mainlist_item_count_tv);
            idTv = (TextView) rootView.findViewById(R.id.mainlist_item_id_tv);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
