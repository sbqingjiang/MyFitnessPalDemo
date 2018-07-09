package com.alan.myfitnesspaldemo.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCall {
    private static ApiService mInstance = null;

    private ApiCall() {}

    /*
     * create singleton api instance
     */
    public static ApiService getInstance() {
        if (mInstance == null) {
            mInstance = getRetrofit().create(ApiService.class);
        }
        return mInstance;
    }
    /*
     * get Retrofit instance from builder
     */
    private static Retrofit getRetrofit() {

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        Interceptor apiKeyInterceptor = chain -> {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter("api-key", ApiService.API_KEY).build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addNetworkInterceptor(new StethoInterceptor() )
                .build();

        return new Retrofit.Builder()
                .baseUrl(ApiService.API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
