package com.waterchen.android_photosignapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.model.entity.StudentEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/5.
 */
public class LessonStudentAdapter extends RecyclerView.Adapter<LessonStudentAdapter.ItemViewHolder> {


    private Context mContext;
    private List<StudentEntity.LessonStudent> mStudnentList;

    private OnItemClickListener mListener;

    public LessonStudentAdapter(Context context) {
        this(context, new ArrayList<StudentEntity.LessonStudent>());
    }

    public LessonStudentAdapter(Context context, List<StudentEntity.LessonStudent> data) {
        this.mContext = context;
        this.mStudnentList = data;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.layout_lesson_student_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        StudentEntity.LessonStudent.Student student = mStudnentList.get(position).number_oi;

        if (student != null) {
            holder.nameTv.setText(student.name);
            holder.collegeTv.setText(student.college);
            holder.gradeTv.setText(student.grade);
            holder.idTv.setText("ID:" + student.number);

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

    public void addStudentData(List<StudentEntity.LessonStudent> data) {
        if (data != null) {
            mStudnentList.clear();
            mStudnentList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mStudnentList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        TextView nameTv;    //学生名称
        TextView gradeTv;   //年级
        TextView idTv;      //学号
        TextView collegeTv; //学院

        public ItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            nameTv = (TextView) rootView.findViewById(R.id.stu_item_name_tv);
            gradeTv = (TextView) rootView.findViewById(R.id.stu_item_grade_tv);
            idTv = (TextView) rootView.findViewById(R.id.stu_item_account_tv);
            collegeTv = (TextView) rootView.findViewById(R.id.stu_item_college_tv);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
