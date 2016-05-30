package com.waterchen.android_photosignapp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class RecordEntity extends BaseResponse<List<RecordEntity.Record>> {

    public class Record {
        @SerializedName("count")
        public int count;
        @SerializedName("date")
        public String date;
        @SerializedName("classid")
        public int classid;
        @SerializedName("totalcount")
        public int totalcount;
    }
}
