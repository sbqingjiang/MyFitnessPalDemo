package com.alan.myfitnesspaldemo.api;

import com.alan.myfitnesspaldemo.api.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    String API_KEY = "d31fe793adf546658bd67e2b6a7fd11a";
    String API_BASE_URL = "https://api.nytimes.com/svc/search/v2/";
    String API_IMAGE_BASE_URL = "http://www.nytimes.com/";

    @GET("articlesearch.json")
    Call<ApiResponse> query(
            @Query("q") String query,
            @Query("fq") String filteredQuery,
            @Query("begin_date") String beginDate,
            @Query("end_date") String endDate,
            @Query("sort") String sort,
            @Query("page") Integer page);
}
