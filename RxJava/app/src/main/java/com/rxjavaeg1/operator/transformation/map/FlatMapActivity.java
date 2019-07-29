package com.rxjavaeg1.operator.transformation.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.rxjavaeg1.NetworkApi.ServiceGenerator;
import com.rxjavaeg1.R;
import com.rxjavaeg1.models.map.Comment;
import com.rxjavaeg1.models.map.Post;
import com.rxjavaeg1.operator.transformation.map.adapter.FlatMapRecyclerAdapter;

import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Transform the item(s) emitted by an Observable into Observables, then flatten the emissions from those into a single Observable.
 * The FlatMap() operator can be very useful in
 * -> 1. Create Observables out of objects emitted by other Observables
 * -> 2. Combine multiple Observable sources into a single Observable (that's what's known as "flattening").
 *
 *  Note:-
 *  Because a single Observable is produced from potentially many sources, the final Observable emits results in a random order.
 *  In other-words, order is not preserved. Depending on your situation, this might matter, this might not.
 */

public class FlatMapActivity extends AppCompatActivity {

    private static final String TAG = "FlatMapActTag";

    //ui
    private RecyclerView recyclerView;

    // vars
    private CompositeDisposable disposables = new CompositeDisposable();
    private FlatMapRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);
        recyclerView = findViewById(R.id.recycler_view);

        initRecyclerView();

        /**
         * @concatMap()-> It emits the object(s) while maintaining order.
         */

        getPostsObservable()
                .subscribeOn(Schedulers.io())                                          // flatmap() for async execution of comments
                .concatMap(new Function<Post, ObservableSource<Post>>() {             //Use here concatMap() for sequential execution of comments
                    @Override
                    public ObservableSource<Post> apply(Post post) throws Exception {
                        return getCommentsObservable(post);    //return an updated Observable<Post> with comments
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Post post) {
                        //Update the post in the List
                        updatePost(post);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private Observable<Post> getCommentsObservable(final Post post){
        return ServiceGenerator.getRequestApi()
                .getComments(post.getId())
                .map(new Function<List<Comment>, Post>() {
                    @Override
                    public Post apply(List<Comment> comments) throws Exception {

                        int delay = ((new Random()).nextInt(5) + 1) * 1000; // sleep thread for x ms
                        Thread.sleep(delay);
                        Log.d(TAG, "apply: sleeping thread " + Thread.currentThread().getName() + " for " + delay + "ms");

                        post.setComments(comments);
                        return post;
                    }
                })
                .subscribeOn(Schedulers.io());

    }

    private Observable<Post> getPostsObservable(){
        return ServiceGenerator.getRequestApi()
                .getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Post>, ObservableSource<Post>>() {
                    @Override
                    public ObservableSource<Post> apply(final List<Post> posts) throws Exception {
                        adapter.setPosts(posts);
                        return Observable.fromIterable(posts)
                                .subscribeOn(Schedulers.io());
                    }
                });
    }

    private void updatePost(Post post){
        adapter.updatePost(post);
    }

    private void initRecyclerView(){
        adapter = new FlatMapRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

}
