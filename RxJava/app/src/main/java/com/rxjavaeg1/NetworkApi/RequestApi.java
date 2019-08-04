package com.rxjavaeg1.NetworkApi;

import com.rxjavaeg1.models.map.Comment;
import com.rxjavaeg1.models.map.Post;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestApi {

    @GET("todos/1")
    Observable<ResponseBody> makeObservableQuery();

    @GET("todos/1")
    Flowable<ResponseBody> makeQuery();

    @GET("posts")
    Observable<List<Post>> getPosts();

    @GET("posts/{id}/comments")
    Observable<List<Comment>> getComments(@Path("id") int id);

    @GET("posts/{id}")
    Observable<Post> getPost( @Path("id") int id);

}
