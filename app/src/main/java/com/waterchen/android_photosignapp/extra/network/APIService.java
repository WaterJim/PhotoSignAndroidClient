package com.waterchen.android_photosignapp.extra.network;

import com.waterchen.android_photosignapp.model.entity.AppInfoEntity;
import com.waterchen.android_photosignapp.model.entity.BaseResponse;
import com.waterchen.android_photosignapp.model.entity.DefaultEntity;
import com.waterchen.android_photosignapp.model.entity.LessonInfoEntity;
import com.waterchen.android_photosignapp.model.entity.RecordEntity;
import com.waterchen.android_photosignapp.model.entity.StudentEntity;
import com.waterchen.android_photosignapp.model.entity.UserEnetity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 橘子哥 on 2016/5/22.
 */
public interface APIService {

    /**
     * 获取服务器端最新的应用信息
     *
     * @return
     */
    @GET("appinfo")
    Observable<AppInfoEntity> getLeastAppInfo();

    /**
     * 用户登录
     *
     * @param account
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<UserEnetity> userLogin(@Field("account") String account,
                                      @Field("password") String password);

    /**
     * 用户注册
     *
     * @param account
     * @param password
     * @param email
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<DefaultEntity> userRegister(@Field("account") String account,
                                           @Field("password") String password,
                                           @Field("email") String email,
                                           @Field("phone") String phone);

    @FormUrlEncoded
    @POST("user/update")
    Observable<UserEnetity> userUpdate(@Field("account") String account,
                                       @Field("name") String name,
                                       @Field("tag") String tag);

    /**
     * 获取最新的课程列表
     *
     * @param token
     * @return
     */
    @GET("class/list/{token}")
    Observable<LessonInfoEntity> getLeastClassList(@Path("token") String token);

    /**
     * 创建课程
     *
     * @param account
     * @param name
     * @param week
     * @return
     */
    @FormUrlEncoded
    @POST("class/create")
    Observable<LessonInfoEntity> doCreateLesson(@Field("account") String account,
                                                @Field("name") String name,
                                                @Field("week") String week);

    /**
     * 添加学生到指定课程中
     *
     * @param account
     * @param stuAccount
     * @param classId
     * @return
     */
    @FormUrlEncoded
    @POST("class/addstudent")
    Observable<DefaultEntity> doAddStudent(@Field("account") String account,
                                           @Field("number") String stuAccount,
                                           @Field("classid") int classId);

    /**
     * 获取指定课程学生列表
     *
     * @param account
     * @param classId
     * @return
     */
    @GET("class/students/{account}/{classid}")
    Observable<StudentEntity> doGetStudentList(@Path("account") String account,
                                               @Path("classid") int classId);


    /**
     * 提交签到记录
     *
     * @param account
     * @param classid
     * @param students
     * @return
     */
    @FormUrlEncoded
    @POST("record/handle")
    Observable<DefaultEntity> uploadRecord(@Field("account") String account,
                                           @Field("classid") int classid,
                                           @Field("students") String students);

    @GET("record/class/{classid}/10")
    Observable<RecordEntity> loadLessonRecord(@Path("classid") int classId);

}
