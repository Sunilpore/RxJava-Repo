package com.rxjavaeg1.operators;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rxjavaeg1.R;
import com.rxjavaeg1.Task;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RangeOperatorActivity extends AppCompatActivity {

    private String TAG = "RangeOptTag";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable <Task> observable = Observable
                .range(0,9)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer,Task>() {
                    @Override
                    public Task apply(Integer integer) throws Exception {
                        Log.d(TAG,"onNext: "+Thread.currentThread().getName());
                        return new Task("New task with priority"+integer,false,integer);
                    }
                })
                .takeWhile(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Exception {
                        return task.getPriority()<9;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());


        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {

                Log.d(TAG,"onNext: "+task.getDescription());
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
