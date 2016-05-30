package com.waterchen.android_photosignapp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class UserEnetity extends BaseResponse<UserEnetity.User> {

    public class User {
        @SerializedName("account")
        public String account;

        @SerializedName("password")
        public String password;

        @SerializedName("sessiontoken")
        public String sessiontoken;

        @SerializedName("nickname")
        public String nickname;

        @SerializedName("tag")
        public String tag;

        @SerializedName("phone")
        public String phone;

        @SerializedName("email")
        public String email;

    }
}
