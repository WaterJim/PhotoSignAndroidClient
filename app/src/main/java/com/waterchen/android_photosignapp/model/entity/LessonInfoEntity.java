package com.waterchen.android_photosignapp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class LessonInfoEntity extends BaseResponse<List<LessonInfoEntity.LessonInfo>> {


    public class LessonInfo {
        @SerializedName("name")
        public String name;

        @SerializedName("createtime")
        public String createtime;

        @SerializedName("lastedittime")
        public String lastedittime;

        @SerializedName("lastuploadtime")
        public String lastuploadtime;

        @SerializedName("uploadtimes")
        public int uploadtimes;

        @SerializedName("week")
        public String week;

        @SerializedName("useraccount")
        public String useraccount;

        @SerializedName("classid")
        public int classid;

        @SerializedName("_id")
        public String _id;

        @SerializedName("count")
        public int count;

    }
}
