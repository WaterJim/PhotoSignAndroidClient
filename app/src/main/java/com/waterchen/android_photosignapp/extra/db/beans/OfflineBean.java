package com.waterchen.android_photosignapp.extra.db.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class OfflineBean extends RealmObject {

    @PrimaryKey
    public String rid;
    public String date;
    public int count;
    public String path;
    public int classid;
    public String classname;
    public String account;
    public boolean upload;

}
