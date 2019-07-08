package com.rxjavaeg1.operator.interval_and_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.rxjavaeg1.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class IntervalActivity extends AppCompatActivity {

    private String TAG = "IntervalActTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        // emit an observable every time interval
        Observable<Long> intervalObservable = Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile(new Predicate<Long>() { // stop the process if more than 5 seconds passes
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        Log.d(TAG,"test: "+aLong+"\t thread: "+Thread.currentThread().getName());
                        return aLong <= 5;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());


        intervalObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {

                Log.d(TAG,"onNext: "+aLong);
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
