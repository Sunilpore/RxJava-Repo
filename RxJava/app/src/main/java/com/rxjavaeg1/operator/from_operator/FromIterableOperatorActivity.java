package com.rxjavaeg1.operator.from_operator;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rxjavaeg1.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FromIterableOperatorActivity extends AppCompatActivity {

    private String TAG = "ItrOptActTag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task("Take out the trash", true, 3));
        taskList.add(new Task("Walk the dog", false, 2));
        taskList.add(new Task("Make my bed", true, 1));
        taskList.add(new Task("Unload the dishwasher", false, 0));
        taskList.add(new Task("Make dinner", true, 5));

        Observable<Task> taskObservable = Observable
                .fromIterable(taskList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext: : " + task.getDescription());
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
