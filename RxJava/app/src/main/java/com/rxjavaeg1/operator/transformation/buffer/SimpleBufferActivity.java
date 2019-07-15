package com.rxjavaeg1.operator.transformation.buffer;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rxjavaeg1.DataSource;
import com.rxjavaeg1.Task;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SimpleBufferActivity extends AppCompatActivity {

    private String TAG = "SmpBufActTag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io());

        taskObservable
                .buffer(2) // Apply the Buffer() operator
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Task>>() { // Subscribe and view the emitted results
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(List<Task> tasks) {
                        Log.d(TAG, "onNext: bundle results: -------------------");
                        for(Task task: tasks){
                            Log.d(TAG, "onNext: " + task.getDescription());
                        }
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
