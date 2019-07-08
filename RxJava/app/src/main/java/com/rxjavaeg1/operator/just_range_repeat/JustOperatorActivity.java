package com.rxjavaeg1.operator.just_range_repeat;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JustOperatorActivity extends AppCompatActivity {

    private String TAG = "JustActTag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Observable <String> observable = Observable.just("first", "second", "third", "fourth", "fifth", "sixth",
                "seventh", "eighth", "ninth", "tenth")
                .subscribeOn(Schedulers.io()) // What thread to do the work on
                .observeOn(AndroidSchedulers.mainThread()); // What thread to observe the results on


        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

                Log.d(TAG,"onNext: "+Thread.currentThread().getName()+"\t string: "+s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }


}
