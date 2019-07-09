package com.rxjavaeg1.operator.take_and_takewhile;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rxjavaeg1.DataSource;
import com.rxjavaeg1.Task;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TakeActivity extends AppCompatActivity {

    private String TAG = "TakeActTag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * @take()-> It will emit only the first 'n' items emitted by an Observable and then complete while ignoring the remaining items.
         */

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .take(3)                                    //Here emit here first take(n) 'n' list and discarded remaining ones
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext: " + task.getDescription());
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
