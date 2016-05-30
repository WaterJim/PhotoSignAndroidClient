package com.waterchen.android_photosignapp.extra.network;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 橘子哥 on 2016/5/22.
 */
public class EasyRetrofit {

    private static final String DEFAULT_SERVICE_URL = "http://119.29.176.102:18004/";
    private static final String LOCALHOST_SERVICE_URL = "http://10.10.117.197:18004/";
    private static final String PHONE_SERVICE_URL = "http://192.168.23.1:18004/";

    private static final boolean isBuild = true;
    private String url = DEFAULT_SERVICE_URL;
    private EasyRetrofitService mService;

    public static EasyRetrofit getInstance() {
        return Singleton.INSTANCE;
    }

    private EasyRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(7676, TimeUnit.MILLISECONDS);

        if (isBuild) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    Logger.d(chain.request().url().toString());
                    return response;
                }
            });
            url = PHONE_SERVICE_URL;

        }

        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        mService = new EasyRetrofitService(retrofit.create(APIService.class));
    }


    private static class Singleton {
        private static final EasyRetrofit INSTANCE = new EasyRetrofit();
    }

    public EasyRetrofitService getAPIService() {
        return mService;
    }
}
