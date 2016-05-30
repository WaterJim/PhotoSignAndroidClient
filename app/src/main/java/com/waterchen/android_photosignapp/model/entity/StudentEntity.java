package com.waterchen.android_photosignapp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class StudentEntity extends BaseResponse<List<StudentEntity.LessonStudent>> {


    public class LessonStudent {

        @SerializedName("number_oi")
        public Student number_oi;


        public class Student {

            @SerializedName("number")
            public String number;

            @SerializedName("name")
            public String name;

            @SerializedName("grade")
            public String grade;

            @SerializedName("college")
            public String college;


        }
    }
}
