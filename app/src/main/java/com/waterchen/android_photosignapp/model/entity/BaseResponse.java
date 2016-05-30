package com.waterchen.android_photosignapp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 橘子哥 on 2016/5/22.
 */
public  class  BaseResponse<T> {

    @SerializedName("statuscode")
    public int statuscode;

    @SerializedName("errormsg")
    public String errormsg;

    @SerializedName("data")
    public T data;

}
