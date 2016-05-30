package com.waterchen.android_photosignapp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class AppInfoEntity extends BaseResponse<AppInfoEntity.AppInfo> {


    public class AppInfo {

        @SerializedName("appname")
        public String appname;

        @SerializedName("version")
        public String version;

        @SerializedName("sign")
        public String sign;

    }
}
