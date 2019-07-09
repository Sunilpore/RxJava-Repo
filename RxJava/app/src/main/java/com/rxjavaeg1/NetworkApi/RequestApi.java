package com.rxjavaeg1.NetworkApi;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface RequestApi {

    @GET("todos/1")
    Observable<ResponseBody> makeObservableQuery();

    @GET("todos/1")
    Flowable<ResponseBody> makeQuery();

}